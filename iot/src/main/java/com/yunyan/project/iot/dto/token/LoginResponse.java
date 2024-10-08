package com.yunyan.project.iot.dto.token;

import java.util.List;

import ch.qos.logback.core.subst.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponse {
    private String code;
    private String msg;
    private List<Token> data;
}