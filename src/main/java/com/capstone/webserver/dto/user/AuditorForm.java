package com.capstone.webserver.dto.user;

import com.capstone.webserver.entity.user.Auditor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class AuditorForm {
    @Schema(description="유저 기본키값")
    private Long idUser;
    @Schema(description="강좌 기본키값")
    private Long idSubject;

    public Auditor toEntity() {
        return Auditor
                .builder()
                .id(null)
                .idUser(idUser)
                .idSubject(idSubject)
                .build();
    }
}
