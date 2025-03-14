package com.yunyan.project.iot.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Token {
    @JsonProperty("access_token")
    private String access_token;
    @JsonProperty("refresh_token")
    private String refresh_token;
    @JsonProperty("token_type")
    private String token_type;
    @JsonProperty("expires_in")
    private Integer expires_in;
}
