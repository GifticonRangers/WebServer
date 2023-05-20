package com.capstone.webserver.dto;

import com.capstone.webserver.entity.nfc.Nfc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class NfcDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class NfcForm {
        @Schema(description = "기본키")
        private Long id;

        @Schema(description = "nfc 고유번호", example = "고유번호")
        private String nfcNumber;

        @Schema(description = "nfc가 위치한 책상의 행 값")
        private int nfcRow;

        @Schema(description = "nfc가 위치한 책상의 열 값")
        private int nfcCol;

        public Nfc toEntity() {
            return Nfc.builder()
                    .nfcNumber(nfcNumber)
                    .nfcRow(nfcRow)
                    .nfcCol(nfcCol)
                    .build();
        }
    }
}
