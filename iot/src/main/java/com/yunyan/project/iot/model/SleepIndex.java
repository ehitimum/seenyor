package com.yunyan.project.iot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SleepIndex {
    private String name;
    private String value;
    private String status;
    private double ratio;
    private String comment;
}
