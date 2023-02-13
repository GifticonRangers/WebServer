package com.capstone.webserver.entity.subject;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GetSubjectJSONModel {
    private Subject[] subject;
}