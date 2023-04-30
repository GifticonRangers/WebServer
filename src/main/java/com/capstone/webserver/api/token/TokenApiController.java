package com.capstone.webserver.api.token;

import com.capstone.webserver.config.jwt.JwtTokenProvider;
import com.capstone.webserver.dto.TokenInfoDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "token", description = "토큰 API")
public class TokenApiController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenInfoDTO.refreshForm dto) {
        String refresh = dto.getRefreshToken();
        TokenInfoDTO accessToken = jwtTokenProvider.refreshToken(refresh);

        return ResponseEntity
                .status(accessToken != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(accessToken);
    }
}
