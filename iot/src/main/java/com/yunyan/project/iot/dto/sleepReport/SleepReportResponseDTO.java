package com.yunyan.project.iot.dto.sleepReport;

import java.util.Map;

import com.yunyan.project.iot.model.SleepData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SleepReportResponseDTO {
    private String code;
    private String msg;
    private Map<String, SleepData> data;
}
