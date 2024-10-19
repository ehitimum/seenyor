package com.yunyan.project.iot.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yunyan.project.iot.dto.DeviceResponse;
import com.yunyan.project.iot.dto.MobileCardResponse;
import com.yunyan.project.iot.dto.MobileResponse;
import com.yunyan.project.iot.dto.bindDevice.bindDTO;
import com.yunyan.project.iot.dto.bindDevice.bindResponse;
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
import com.yunyan.project.iot.dto.subscribeAffair.CallBackResponse;
import com.yunyan.project.iot.dto.subscribeAffair.DeviceEventNotificationDTO;
import com.yunyan.project.iot.dto.subscribeAffair.SubscribeResponse;
import com.yunyan.project.iot.dto.subscribeAffair.subscribeDTO;
import com.yunyan.project.iot.dto.token.LoginDTO;
import com.yunyan.project.iot.dto.token.LoginResponse;
import com.yunyan.project.iot.dto.whitelist.WhiteResponse;
import com.yunyan.project.iot.dto.whitelist.WhitelistDTO;
import com.yunyan.project.iot.util.Sha1Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceService {

    @Autowired
    private final RestTemplate restTemplate;
    private String appId = "ql763202409240025027482";
    private String secret = "180cb5ecb949465e60b39944b26535e8869985b4";
   
    public <T> DeviceResponse getDeviceInfo() throws NoSuchAlgorithmException{
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/getDeviceInfo";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String concatenatedString = secret + "#" + timestamp + "#";
        String signature = Sha1Util.generateSha1(concatenatedString);
        HttpEntity<?> entity = new HttpEntity<>(headerBuilder(appId, timestamp, signature));
        return httpRequestBuilder(entity, apiUrl, DeviceResponse.class, HttpMethod.GET);
    }

    private HttpHeaders headerBuilder(String appId, String timestamp, String signature){
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        return headers;
    }

    private HttpHeaders headerBuilder(String appId, String timestamp, String signature, MediaType type){
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        headers.setContentType(type);
        return headers;
    }

    private <T> T httpRequestBuilder(HttpEntity<?> entity, String apiUrl, Class<T> responseType, HttpMethod method){
        ResponseEntity<T> response = restTemplate.exchange(
                apiUrl,
                method,
                entity,
                responseType
        );
        return response.getBody();
    }

    public DeviceResponse enableMqttPush(MqttEnableDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdmqtt/v2/dept/opmqtt";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Map<String, Object> body = new HashMap<>();
        
        // Set request body parameters
        body.put("uid", request.getUid());
        body.put("server", request.getServer());
        body.put("port", request.getPort());
        body.put("username", request.getUsername());
        body.put("password", request.getPassword());
        body.put("clientId", request.getClientId());
        body.put("keepAlive", request.getKeepAlive());
        body.put("messageType", request.getMessageType());
        
        if (request.getTopics() != null && request.getTopics().getPub() != null) {
            Map<String, List<String>> topics = new HashMap<>();
            topics.put("pub", request.getTopics().getPub());
            body.put("topics", topics);
        }

        // Concatenate and create signature (same as before)
        Map<String, String> params = new TreeMap<>();
        params.put("uid", request.getUid());
        params.put("server", request.getServer());
        params.put("port", String.valueOf(request.getPort()));
        params.put("username", request.getUsername());
        params.put("password", request.getPassword());
        params.put("clientId", request.getClientId());
        params.put("keepAlive", String.valueOf(request.getKeepAlive()));
        params.put("messageType", String.join("=", request.getMessageType().stream()
                                        .map(String::valueOf)
                                        .toArray(String[]::new)));
        if (request.getTopics() != null && request.getTopics().getPub() != null) {
            params.put("topics", "pub=" + String.join("=", request.getTopics().getPub()));
        }

        String concatenatedParams = params.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("#"));
        
        
        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));
        return httpRequestBuilder(entity, apiUrl, DeviceResponse.class, HttpMethod.POST);
}

    public PropertiesDTO getDeviceProp() throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/deviceProp?uid=3525E3DD5D9B";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String concatenatedString = secret + "#" + timestamp + "#" + "uid=3525E3DD5D9B#"; //+ concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<?> entity = new HttpEntity<>(headerBuilder(appId, timestamp, signature));
        return httpRequestBuilder(entity, apiUrl, PropertiesDTO.class, HttpMethod.GET);
    }

    public MqttDisableResponse disableMqtt(MqttDisableDTO request) throws NoSuchAlgorithmException {
        
        String apiUrl = "https://qinglanst.com/prod-api/thirdmqtt/v2/dept/clmqtt";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        body.put("messageType", request.getMessageType());
        Map<String, String> params = new TreeMap<>();
        params.put("uid", request.getUid());
        params.put("messageType", String.join("=", request.getMessageType().stream()
                                        .map(String::valueOf)
                                        .toArray(String[]::new)));
        
        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));
        return httpRequestBuilder(entity, apiUrl, MqttDisableResponse.class, HttpMethod.POST);
    }

    public bindResponse bindDevice(bindDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/bindDevice";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        Map<String, String> params = new TreeMap<>();
        params.put("uid", request.getUid());
        
        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));
        return httpRequestBuilder(entity, apiUrl, bindResponse.class, HttpMethod.POST);
    }

    public bindResponse unbindDevice(bindDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/unbindDevice";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        Map<String, String> params = new TreeMap<>();
        params.put("uid", request.getUid());
        
        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));
        return httpRequestBuilder(entity, apiUrl, bindResponse.class, HttpMethod.POST);
    }

    public SubscribeResponse subscribeAffair(subscribeDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/unbindDevice";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("event", request.getEvent());
        body.put("humanUrl", request.getHumanUrl());
        body.put("eventUrl", request.getEventUrl());

        Map<String, String> params = new TreeMap<>();
        params.put("event", String.join("=", request.getEvent().stream()
                                        .map(String::valueOf)
                                        .toArray(String[]::new )));
        params.put("humanUrl", request.getHumanUrl());
        params.put("eventUrl", request.getEventUrl());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, SubscribeResponse.class, HttpMethod.POST);
    }

    public PropertiesDTO setDeviceProp(DevicePropertiesDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/prop";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        body.put("scene", request.getScene());
        body.put("installType", request.getInstallType());
        body.put("height", request.getHeight());
        body.put("susFallDuration", request.getSusFallDuration());
        body.put("leaveAlarmSwitch", request.getLeaveAlarmSwitch());
        body.put("leaveDetectionTime", request.getLeaveDetectionTime());
        body.put("leaveDetectionRange", request.getLeaveDetectionRange());
        body.put("longAwaySwitch", request.getLongAwaySwitch());
        body.put("detentionAlarmSwitch", request.getDetentionAlarmSwitch());
        body.put("entryDetectionTime", request.getEntryDetectionTime());

        Map<String, String> params = new TreeMap<>();
        params.put("uid", request.getUid());
        params.put("scene", request.getScene());
        params.put("installType", request.getInstallType());
        params.put("height", request.getHeight());
        params.put("susFallDuration", request.getSusFallDuration());
        params.put("leaveAlarmSwitch", request.getLeaveAlarmSwitch());
        params.put("leaveDetectionTime", request.getLeaveDetectionTime());
        params.put("leaveDetectionRange", request.getLeaveDetectionRange());
        params.put("longAwaySwitch", request.getLongAwaySwitch());
        params.put("detentionAlarmSwitch", request.getDetentionAlarmSwitch());
        params.put("entryDetectionTime", request.getEntryDetectionTime());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, PropertiesDTO.class, HttpMethod.POST);
    }

    public DeviceResponse setDeviceBoundary(BoundariesDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/border";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        body.put("upLeft", request.getUpLeft());
        body.put("lowLeft", request.getLowLeft());
        body.put("upRight", request.getUpRight());
        body.put("lowRight", request.getLowRight());

        Map<String, Object> params = new HashMap<>();
        params.put("uid", request.getUid());
        params.put("upLeft", request.getUpLeft());
        params.put("lowLeft", request.getLowLeft());
        params.put("upRight", request.getUpRight());
        params.put("lowRight", request.getLowRight());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, DeviceResponse.class, HttpMethod.POST);
    }

    public DeviceResponse setDeviceArea(DeviceAreaDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/coords";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        body.put("coordKey", request.getCoordKey());
        body.put("type", request.getType());
        body.put("upLeft", request.getUpLeft());
        body.put("lowLeft", request.getLowLeft());
        body.put("upRight", request.getUpRight());
        body.put("lowRight", request.getLowRight());

        Map<String, Object> params = new HashMap<>();
        params.put("uid", request.getUid());
        params.put("coordKey", request.getCoordKey());
        params.put("type", request.getType());
        params.put("upLeft", request.getUpLeft());
        params.put("lowLeft", request.getLowLeft());
        params.put("upRight", request.getUpRight());
        params.put("lowRight", request.getLowRight());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, DeviceResponse.class, HttpMethod.POST);
    }

    public breathheartResponse setBreathHeartParam(BreathHeartParamDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/threshold";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        body.put("upperBreathe", request.getUpperBreathe());
        body.put("upperHeartRate", request.getUpperHeartRate());
        body.put("lowerBreathe", request.getLowerBreathe());
        body.put("lowerHeartRate", request.getLowerHeartRate());
        body.put("intensiveCare", request.getIntensiveCare());
        body.put("suddenDuration", request.getSuddenDuration());
        body.put("sensitivity", request.getSensitivity());

        Map<String, Object> params = new HashMap<>();
        params.put("uid", request.getUid());
        params.put("upperBreathe", request.getUpperBreathe());
        params.put("upperHeartRate", request.getUpperHeartRate());
        params.put("lowerBreathe", request.getLowerBreathe());
        params.put("lowerHeartRate", request.getLowerHeartRate());
        params.put("intensiveCare", request.getIntensiveCare());
        params.put("suddenDuration", request.getSuddenDuration());
        params.put("sensitivity", request.getSensitivity());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, breathheartResponse.class, HttpMethod.POST);
    }

    public MobileResponse getPhoneNumber() throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/getCallee?uid=25A859B81A6F";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String concatenatedString = secret + "#" + timestamp + "#" + "uid=25A859B81A6F#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<?> entity = new HttpEntity<>(headerBuilder(appId, timestamp, signature));
        return httpRequestBuilder(entity, apiUrl, MobileResponse.class, HttpMethod.GET);
    }
    
    public MobileCardResponse getCardInfo() throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/cardInfo?uid=25A859B81A6F";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String concatenatedString = secret + "#" + timestamp + "#" + "uid=25A859B81A6F#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<?> entity = new HttpEntity<>(headerBuilder(appId, timestamp, signature));
        return httpRequestBuilder(entity, apiUrl, MobileCardResponse.class, HttpMethod.GET);
    }

    public WhiteResponse getWhitelist() throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/whiteList?uid=25A859B81A6F";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String concatenatedString = secret + "#" + timestamp + "#" + "uid=25A859B81A6F#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<?> entity = new HttpEntity<>(headerBuilder(appId, timestamp, signature));
        return httpRequestBuilder(entity, apiUrl, WhiteResponse.class, HttpMethod.GET);
    }

    public LoginResponse getToken(LoginDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/login";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("username", request.getUsername());
        body.put("password", request.getPassword());
        body.put("pattern", request.getPattern());
        body.put("grantType", request.getGrantType());

        Map<String, String> params = new TreeMap<>();
        params.put("username", request.getUsername());
        params.put("password", request.getPassword());
        params.put("pattern", request.getPattern());
        params.put("grantType", request.getGrantType());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, LoginResponse.class, HttpMethod.POST);
    }
    public LoginResponse getMiniToken() throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/applet/auth";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        String concatenatedString = secret + "#" + timestamp + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, LoginResponse.class, HttpMethod.POST);
    }

    public WhiteResponse addWhitelis(WhitelistDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/whiteAdd";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        body.put("mobile", request.getMobile());

        Map<String, String> params = new TreeMap<>();
        params.put("uid", request.getUid());
        params.put("mobile", request.getMobile());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        String signature = Sha1Util.generateSha1(concatenatedString);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, WhiteResponse.class, HttpMethod.POST);
    }

    public WhiteResponse delWhiteList(WhitelistDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/whiteDelete";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        
        Map<String, Object> body = new HashMap<>();
        body.put("uid", request.getUid());
        body.put("mobile", request.getMobile());

        Map<String, String> params = new TreeMap<>();
        params.put("uid", request.getUid());
        params.put("mobile", request.getMobile());

        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, WhiteResponse.class, HttpMethod.POST);
    }

    public SleepReportResponseDTO getSleepReport(SleepReportRequestDTO request) throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/report";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);
        Map<String, Object> body = new HashMap<>();
        body.put("uids", request.getUids());
        body.put("date", request.getDate());

        Map<String, Object> params = new HashMap<>();
        params.put("uids",  String.join("=", request.getUids().stream()
                                                    .map(String::valueOf)
                                                    .toArray(String[]::new)));
        params.put("date", request.getDate());
        String concatenatedParams = params.entrySet().stream()
                                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                                        .collect(Collectors.joining("#"));

        String concatenatedString = secret + "#" + timestamp + "#" + concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, SleepReportResponseDTO.class, HttpMethod.POST);
    }

    public RiskRankingResponseDTO getRiskRanking() throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/riskRanking";
        String timestamp = String.valueOf(System.currentTimeMillis()/ 1000);

        String concatenatedString = secret + "#" + timestamp + "#";
        String signature = Sha1Util.generateSha1(concatenatedString);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headerBuilder(appId, timestamp, signature, MediaType.APPLICATION_JSON));        
        return httpRequestBuilder(entity, apiUrl, RiskRankingResponseDTO.class, HttpMethod.POST);
    }

    public ResponseEntity<CallBackResponse> handleHeadcountChange(DeviceEventNotificationDTO notification) {
        int count = (int) notification.getParams().get("count");
        DeviceEventNotificationDTO events = DeviceEventNotificationDTO.builder()
                                        .cmd(notification.getCmd())
                                        .uid(notification.getUid())
                                        .event(notification.getEvent())
                                        .count(count)
                                        .build();
        CallBackResponse response = CallBackResponse.builder()
                                        .code("200")
                                        .msg("Operation Successfull")
                                        .data(events)
                                        .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void handleFallIncident(DeviceEventNotificationDTO notification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handleBreathAndHeartRate(DeviceEventNotificationDTO notification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handleDoorEntryAndExit(DeviceEventNotificationDTO notification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handleDeviceOfflineOnline(DeviceEventNotificationDTO notification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handPoorEvent(DeviceEventNotificationDTO notification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handleCommonAlarm(DeviceEventNotificationDTO notification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handleEnteringExitingAlarmArea(DeviceEventNotificationDTO notification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   






}
