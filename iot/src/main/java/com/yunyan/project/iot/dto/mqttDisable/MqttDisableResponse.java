package com.yunyan.project.iot.dto.mqttDisable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MqttDisableResponse {
    private String code;
    private String msg;
}