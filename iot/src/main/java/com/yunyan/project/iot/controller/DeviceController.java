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
import com.yunyan.project.iot.dto.MobileCardResponse;
import com.yunyan.project.iot.dto.MobileResponse;
import com.yunyan.project.iot.dto.bindDevice.bindDTO;
import com.yunyan.project.iot.dto.boundary.BoundariesDTO;
import com.yunyan.project.iot.dto.boundary.DeviceAreaDTO;
import com.yunyan.project.iot.dto.breathheart.BreathHeartParamDTO;
import com.yunyan.project.iot.dto.breathheart.breathheartResponse;
import com.yunyan.project.iot.dto.mqttDisable.MqttDisableDTO;
import com.yunyan.project.iot.dto.mqttDisable.MqttDisableResponse;
import com.yunyan.project.iot.dto.mqttEnable.MqttEnableDTO;
import com.yunyan.project.iot.dto.properties.DevicePropertiesDTO;
import com.yunyan.project.iot.dto.properties.PropertiesDTO;
import com.yunyan.project.iot.dto.riskRanking.RiskRankingResponseDTO;
import com.yunyan.project.iot.dto.sleepReport.SleepReportRequestDTO;
import com.yunyan.project.iot.dto.sleepReport.SleepReportResponseDTO;
import com.yunyan.project.iot.dto.subscribeAffair.SubscribeResponse;
import com.yunyan.project.iot.dto.subscribeAffair.subscribeDTO;
import com.yunyan.project.iot.dto.token.LoginDTO;
import com.yunyan.project.iot.dto.token.LoginResponse;
import com.yunyan.project.iot.dto.whitelist.WhiteResponse;
import com.yunyan.project.iot.dto.whitelist.WhitelistDTO;
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

    @PostMapping("/properties")
    public PropertiesDTO setDeviceProp(@RequestBody @Validated DevicePropertiesDTO request) throws  NoSuchAlgorithmException{
        return service.setDeviceProp(request);
    }

    @PostMapping("/boundaries")
    public DeviceResponse setDeviceBoundary(@RequestBody @Validated BoundariesDTO request) throws  NoSuchAlgorithmException{
        return service.setDeviceBoundary(request);
    }

    @PostMapping("/area")
    public DeviceResponse setDeviceArea(@RequestBody @Validated DeviceAreaDTO request) throws  NoSuchAlgorithmException{
        return service.setDeviceArea(request);
    }

    @PostMapping("/breath-heart")
    public breathheartResponse setBreathHeartParam(@RequestBody @Validated BreathHeartParamDTO request) throws NoSuchAlgorithmException{
        return service.setBreathHeartParam(request);
    }

    @GetMapping("/phone")
    public MobileResponse getPhoneNumber() throws  NoSuchAlgorithmException{
        return service.getPhoneNumber();
    }

    @GetMapping("/phone/sim")
    public MobileCardResponse getCardInfo() throws  NoSuchAlgorithmException{
        return service.getCardInfo();
    }

    @GetMapping("/phone/sim/whitelist")
    public WhiteResponse getWhitelist() throws  NoSuchAlgorithmException{
        return service.getWhitelist();
    }

    @GetMapping("/login")
    public LoginResponse getToken(@RequestBody @Validated LoginDTO request) throws  NoSuchAlgorithmException{
        return service.getToken(request);
    }
    @PostMapping("/phone/sim/whitelist")
    public WhiteResponse addWhitelist(@RequestBody @Validated WhitelistDTO request)throws  NoSuchAlgorithmException{
        return service.addWhitelis(request);
    }
    @PostMapping("/phone/sim/whitelist/delete")
    public WhiteResponse delWhiteList(@RequestBody @Validated WhitelistDTO request)throws  NoSuchAlgorithmException{
        return service.delWhiteList(request);
    }

    @PostMapping("/sleepReport")
    public SleepReportResponseDTO getSleepReport(@RequestBody @Validated SleepReportRequestDTO request)throws NoSuchAlgorithmException{
        return service.getSleepReport(request);
    }

    @PostMapping("/riskranking")
    public RiskRankingResponseDTO getRiskRanking()throws  NoSuchAlgorithmException{
        return service.getRiskRanking();
    }


}
