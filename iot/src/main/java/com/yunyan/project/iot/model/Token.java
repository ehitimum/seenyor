package com.yunyan.project.iot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Token {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
}
