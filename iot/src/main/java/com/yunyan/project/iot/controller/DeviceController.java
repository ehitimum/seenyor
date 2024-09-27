package com.yunyan.project.iot.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunyan.project.iot.dto.DeviceResponse;
import com.yunyan.project.iot.dto.MqttEnableDTO;
import com.yunyan.project.iot.service.DeviceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {
    @Autowired
    private final DeviceService service;

    @GetMapping
    public DeviceResponse getDeviceInfo() throws NoSuchAlgorithmException {
        return service.getDeviceInfo();
    }
    
    @PostMapping
    public DeviceResponse startMqtt(@RequestBody @Validated MqttEnableDTO request) throws NoSuchAlgorithmException{
        return service.enableMqttPush(request);
    }
}
