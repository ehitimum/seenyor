package com.yunyan.project.iot.dto;

import java.util.List;

import com.yunyan.project.iot.model.Device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DeviceResponse {
    private String code;
    private String msg;
    private List<Device> data;
}
