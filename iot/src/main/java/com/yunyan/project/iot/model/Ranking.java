package com.yunyan.project.iot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {
    private String oldManName;
    private int score;
    private String eqtName;
    private String uid;
    private Integer age;      
    private Integer enjoyDays;
    private String gender;
}
