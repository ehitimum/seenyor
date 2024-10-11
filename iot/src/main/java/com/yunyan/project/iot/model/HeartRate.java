package com.yunyan.project.iot.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartRate {
    private int min;
    private int avg;
    private int max;
    private String avgLabel;
    private List<DataPoint> dataList;
}
