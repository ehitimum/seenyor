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
import com.yunyan.project.iot.dto.bindDevice.bindDTO;
import com.yunyan.project.iot.dto.mqttDisable.MqttDisableDTO;
import com.yunyan.project.iot.dto.mqttDisable.MqttDisableResponse;
import com.yunyan.project.iot.dto.mqttEnable.MqttEnableDTO;
import com.yunyan.project.iot.dto.properties.PropertiesDTO;
import com.yunyan.project.iot.dto.subscribeAffair.SubscribeResponse;
import com.yunyan.project.iot.dto.subscribeAffair.subscribeDTO;
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
    @GetMapping("/properties")
    public  PropertiesDTO getDeviceProp() throws  NoSuchAlgorithmException{
        return service.getDeviceProp();
    }

    @PostMapping("/bind")
    public DeviceResponse bindDevice(@RequestBody @Validated bindDTO request) throws NoSuchAlgorithmException{
        return service.bindDevice(request);
    }
    @PostMapping("/unbind")
    public DeviceResponse unbindDevice(@RequestBody @Validated bindDTO request) throws NoSuchAlgorithmException{
        return service.unbindDevice(request);
    }
    @PostMapping("/subcribe")
    public SubscribeResponse subscribeAffair(@RequestBody @Validated subscribeDTO request) throws NoSuchAlgorithmException{
        return service.subscribeAffair(request);
    }
    
    @PostMapping("/Mqtt/Start")
    public DeviceResponse startMqtt(@RequestBody @Validated MqttEnableDTO request) throws NoSuchAlgorithmException{
        return service.enableMqttPush(request);
    }
    @PostMapping("/Mqtt/Stop")
    public MqttDisableResponse stopMqtt(@RequestBody @Validated MqttDisableDTO request) throws  NoSuchAlgorithmException{
        return service.disableMqtt(request);
    }



}
