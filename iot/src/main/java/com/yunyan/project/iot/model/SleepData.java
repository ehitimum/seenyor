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
public class SleepData {
    private String uid;
    private String date;
    private int score;
    private int fallScore;
    private double sleepEfficiency;
    private double sleepQuality;
    private String sleepTotalTime;
    private String onBedTime;
    private String scoreLabel;
    private int leaveBedCount;
    private String getBedIdx;
    private String sleepStIdx;
    private String sleepEdIdx;
    private String leaveBedIdx;
    private String ahi;

    //private BreathRate breathRateVo;
    //private HeartRate heartRateVo;
    private List<SleepIndex> sleepIndexCommonList;
    private List<StatisticalData> statisticalData;
    private List<String> timestamps;
    private List<AlarmEvents> alarmEvents;
    private Evaluation evaluation;
    private UserActivity userActivity;
}
