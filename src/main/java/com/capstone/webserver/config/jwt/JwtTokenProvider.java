package com.capstone.webserver.config.jwt;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.TokenInfoDTO;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static com.capstone.webserver.config.error.ErrorCode.*;

@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    UserRepository userRepository;
    private final Key accessTokenKey;
    private final Key refreshTokenKey;

    public JwtTokenProvider(@Value("${jwt.secret}") String accessTokenSecret,
                            @Value("${jwt.secret}") String refreshTokenSecret) {
        byte[] accessTokenBytes = Decoders.BASE64.decode(accessTokenSecret);
        this.accessTokenKey = Keys.hmacShaKeyFor(accessTokenBytes);

        byte[] refreshTokenBytes = Decoders.BASE64.decode(refreshTokenSecret);
        this.refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenBytes);
    }

    // Refresh Token이 발급되었는지 확인하는 메서드
    private boolean hasRefreshToken(String idUser) {
        String refreshToken = userRepository.findRefreshTokenByIdUser(idUser);
        return refreshToken != null;
    }

    private void setRefreshToken(String idUser, String refreshToken) {
        com.capstone.webserver.entity.user.User user = userRepository.findByIdUser(idUser).orElseThrow(() -> new CustomException(BadRequest));
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Transactional
    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfoDTO generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 10800000);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(accessTokenKey, SignatureAlgorithm.HS256)
                .compact();

        String idUser = authentication.getName();
        boolean refreshTokenExists = hasRefreshToken(idUser);

        com.capstone.webserver.entity.user.User user = userRepository.findByIdUser(idUser).orElseThrow(() -> new CustomException(BadRequest));
        log.info(String.valueOf(user));

        Date refreshTokenExpiresIn = new Date(now + 24 * 30 * 60 * 60);

        if (refreshTokenExists) {
            // 이미 Refresh Token이 DB 상에 저장되어 있는 경우
            String existingRefreshToken = userRepository.findRefreshTokenByIdUser(idUser);

            try {
                Date existingRefreshTokenExpirationDate = Jwts.parserBuilder()
                        .setSigningKey(refreshTokenKey)
                        .build()
                        .parseClaimsJws(existingRefreshToken)
                        .getBody()
                        .getExpiration();
            } catch (ExpiredJwtException e) {
                Date nowDate = new Date();
                log.info("1234");
                // Refresh Token이 만료된 경우
                String newRefreshToken = Jwts.builder()
                        .setSubject(idUser)
                        .claim("auth", authorities)
                        .setExpiration(refreshTokenExpiresIn)
                        .signWith(refreshTokenKey, SignatureAlgorithm.HS256)
                        .compact();
                setRefreshToken(idUser, newRefreshToken);
                log.info("Refresh Token이 만료되어 새로운 토큰 발급: " + newRefreshToken);
            }
        } else {
            // 최초 로그인 시 Refresh Token 발급
            String refreshToken = Jwts.builder()
                    .setSubject(idUser)
                    .claim("auth", authorities)
                    .setExpiration(refreshTokenExpiresIn)
                    .signWith(refreshTokenKey, SignatureAlgorithm.HS256)
                    .compact();

            setRefreshToken(idUser, refreshToken);
            log.info("로그인을 통한 발급: " + refreshToken);
        }

        // User Entity에 idUser 정보 추가
        user.setIdUser(idUser);

        if (user.getRefreshToken() == null) {
            throw new CustomException(BadRequest);
        }

        log.info("Before save: " + user.toString());
        userRepository.save(user);
        log.info("After save: " + userRepository.findById(user.getId()).orElseThrow(() -> new CustomException(BadRequest)).toString());

        TokenInfoDTO tokenInfoDTO = TokenInfoDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(user.getRefreshToken())
                .build();

        log.info(tokenInfoDTO.toString());

        return tokenInfoDTO;
    }

    public TokenInfoDTO refreshToken(String refreshToken) {
        try {
            log.info("입력한 값: " + refreshToken);

            if (refreshToken == null) {
                log.info("refreshToken is Null");
                throw new CustomException(BadRequest);
            }

            // 1. 전달된 refreshToken이 유효한 token인지 확인
            Jwts.parserBuilder().setSigningKey(refreshTokenKey).build().parseClaimsJws(refreshToken);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(refreshTokenKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
            log.info("claims: " + claims);

            String idUser = claims.getSubject();
            log.info("idUser 값이 " + idUser + "인 유저에 대한 refresh token을 확인합니다.");

            // 2. DB에서 해당 유저의 refreshToken을 가져옴
            log.info("DB 확인을 시작합니다.");
            String dbRefreshToken = userRepository.findRefreshTokenByIdUser(idUser);
            log.info("DB 확인이 완료되었습니다. DB 내 refresh token 값: " + dbRefreshToken);

            if (dbRefreshToken == null) {
                throw new CustomException(BadRequest);
            }

            log.info(String.valueOf(dbRefreshToken.equals(refreshToken)));
            // 3. refreshToken과 DB에서 가져온 refreshToken 값을 비교
            if (!dbRefreshToken.equals(refreshToken)) {
                throw new CustomException(INVALID_JWT_TOKEN);
            }

            // 4. Authentication 객체를 가져와, 새로운 토큰 생성
            Authentication authentication = getAuthentication(refreshToken);
            return generateToken(authentication);

        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new CustomException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new CustomException(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new CustomException(NON_LOGIN);
        }
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String token) {
        // 토큰 복호화
        Claims claims = parseClaims(token);

        if (claims.get("auth") == null) {
            throw new CustomException(Forbidden);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new CustomException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new CustomException(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new CustomException(NON_LOGIN);
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}