package com.yunyan.project.iot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceProperties {
    private String radar_func_ctrl;
    private String radar_install_style;
    private String suspected_fall_time;
    private String radar_install_height;
    private String rectangle;
    private String declare_area;
    private String heart_breath_param;
    private String signal_intensity;
    private String accelera;
    private String radar_compile_time;
    private String app_compile_time;

}
