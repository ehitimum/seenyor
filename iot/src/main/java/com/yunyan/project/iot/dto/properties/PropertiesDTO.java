package com.yunyan.project.iot.dto.properties;

import java.util.List;

import com.yunyan.project.iot.model.DeviceProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesDTO {
    private String code;
    private String msg;
    private List<DeviceProperties> data;
}
