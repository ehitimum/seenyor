package com.yunyan.project.iot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Evaluation {
    private String sleepDeepRatioEvaluation;
    private String sleepLeaveBedEvaluation;
    private String sleepStartTimeEvaluation;
    private String sleepDurationEvaluation;
    private String sleepAHIEvaluation;
    private String ahiUnqualifiedEvaluation;
    private String ahiAnalysisEvaluation;
    private String unqualifiedEvaluation;
    private String sleepAnalysisEvaluation;
}
