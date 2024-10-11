package com.yunyan.project.iot.dto.riskRanking;

import com.yunyan.project.iot.model.RiskRanking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiskRankingResponseDTO {
    private String code;
    private String msg;
    private RiskRanking data;
}
