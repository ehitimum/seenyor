package com.yunyan.project.iot.dto.mqttDisable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MqttDisableDTO {
    private String uid;
    private List<Integer> messageType;
}
