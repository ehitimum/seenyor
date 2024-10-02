package com.yunyan.project.iot.dto.subscribeAffair;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SubscribeResponse {
    private String code;
    private String msg;
}
