package com.yunyan.project.iot.dto.subscribeAffair;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CallBackResponse {
    private String code;
    private String msg;
    private DeviceEventNotificationDTO data;
}
