package com.yunyan.project.iot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity {
    private int entryRoomCount;
    private String inRoomDuration;
    private String otherDuration;
    private String otherDurationRatio;
    private String speed;
    private String staticDuration;
    private String staticDurationRatio;
    private int stepNumber;
    private String walkDuration;
    private String walkDurationRatio;
}
