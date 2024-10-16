package com.yunyan.project.iot.dto.subscribeAffair;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEventNotificationDTO {
    private String cmd;
    private String uid;
    private String event;
    private String eventName;
    private Map<String, Object> params;
}
