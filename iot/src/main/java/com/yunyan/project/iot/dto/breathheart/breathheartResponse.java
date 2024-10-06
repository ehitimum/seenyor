package com.yunyan.project.iot.dto.breathheart;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class breathheartResponse {
    private String code;
    private String msg;
    private Map<String, Object> data;
}
