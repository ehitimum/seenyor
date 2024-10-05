package com.yunyan.project.iot.dto.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DevicePropertiesDTO {
    private String uid;
    private String scene;
    private String installType;
    private String height;
    private String susFallDuration;
    private String leaveAlarmSwitch;
    private String leaveDetectionTime;
    private String leaveDetectionRange;
    private String longAwaySwitch;
    private String detentionAlarmSwitch;
    private String entryDetectionTime;
}
