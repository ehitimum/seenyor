package com.yunyan.project.iot.dto.mqttEnable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MqttEnableDTO {
    private String uid;
    private String server;
    private String password;
    private String clientId;
    private int keepAlive;
    private List<Integer> messageType;
    private int port;
    private TopicsDTO topics;
    private String username;
    

}
