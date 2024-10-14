package com.yunyan.project.iot.dto.breathheart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BreathHeartParamDTO {
    private String uid;                
    private int upperBreathe;          
    private int upperHeartRate;       
    private int lowerBreathe;          
    private int lowerHeartRate;      
    private int intensiveCare;     
    private int suddenDuration;  
    private int sensitivity;    
}
