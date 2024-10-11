package com.yunyan.project.iot.dto.sleepReport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SleepReportRequestDTO {
    private List<String> uids;
    private String date;
}
