package com.capstone.webserver.entity.nfc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nfc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "nfc 기본키값")
    private Long id;

    @Column
    @Schema(description = "nfc 고유번호")
    private String nfcNumber;

    @Column
    @Schema(description = "nfc가 위치한 책상의 행 값")
    private int nfcRow;

    @Column
    @Schema(description = "nfc가 위치한 책상의 열 값")
    private int nfcCol;
}
