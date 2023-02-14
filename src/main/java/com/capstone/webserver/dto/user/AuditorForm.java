package com.capstone.webserver.dto.user;

import com.capstone.webserver.entity.user.Auditor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class AuditorForm {
    private Long id;
    private Long idSubject;

    public Auditor toEntity() {
        return Auditor
                .builder()
                .idStudent(id)
                .idSubject(idSubject)
                .build();
    }
}
