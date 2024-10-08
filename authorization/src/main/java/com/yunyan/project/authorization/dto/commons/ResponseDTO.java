package com.yunyan.project.authorization.dto.commons;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
    private int statusCode;
    private String message;
    private boolean status;
    private T data;
}
