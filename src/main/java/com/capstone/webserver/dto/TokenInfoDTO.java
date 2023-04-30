package com.capstone.webserver.dto;

import com.capstone.webserver.entity.user.Auditor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class TokenInfoDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class refreshForm {
        @Schema(description="refreshToken")
        private String refreshToken;

        public TokenInfoDTO toEntity() {
            return TokenInfoDTO
                    .builder()
                    .refreshToken(refreshToken)
                    .build();
        }
    }
}
