package com.yunyan.project.iot.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginDTO {
    private String username;
    private String password;
    private String pattern;
    private String grantType;
}
