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
public class RiskRanking {
    private List<Ranking> fallAlarmRaking;
    private List<Ranking> aggregateScoreRaking;
}
