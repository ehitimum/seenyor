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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yunyan.project.iot.dto.DeviceResponse;
import com.yunyan.project.iot.dto.bindDevice.bindDTO;
import com.yunyan.project.iot.dto.mqttDisable.MqttDisableDTO;
import com.yunyan.project.iot.dto.mqttDisable.MqttDisableResponse;
import com.yunyan.project.iot.dto.mqttEnable.MqttEnableDTO;
import com.yunyan.project.iot.dto.properties.PropertiesDTO;
import com.yunyan.project.iot.dto.subscribeAffair.SubscribeResponse;
import com.yunyan.project.iot.dto.subscribeAffair.subscribeDTO;
import com.yunyan.project.iot.util.Sha1Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceService {

    @Autowired
    private final RestTemplate restTemplate;
    private String appId = "ql763202409240025027482";
    private String secret = "173426b4a40a719822720489dd5b6ea03224c9d9";
   
    public DeviceResponse getDeviceInfo() throws NoSuchAlgorithmException{
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/getDeviceInfo";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String concatenatedString = secret + "#" + timestamp + "#";
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<DeviceResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                DeviceResponse.class
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

        // Create headers and add Content-Type
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        headers.setContentType(MediaType.APPLICATION_JSON);  // Set Content-Type to application/json

        // Create HttpEntity with body and headers
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Make POST request
        ResponseEntity<DeviceResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                DeviceResponse.class
        );
        
        return response.getBody();
}

    public PropertiesDTO getDeviceProp() throws NoSuchAlgorithmException {
        String apiUrl = "https://qinglanst.com/prod-api/thirdparty/v2/deviceProp?uid=25A859B81A6F";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        // Map<String, String> params = new TreeMap<>();
    
        // params.put("uid", request.getUid());

        //  String concatenatedParams = params.entrySet().stream()
        //     .map(entry -> entry.getKey() + "=" + entry.getValue())
        //     .collect(Collectors.joining("#"));
    
        String concatenatedString = secret + "#" + timestamp + "#" + "uid=25A859B81A6F#"; //+ concatenatedParams + "#";
        System.out.println(concatenatedString);
        String signature = Sha1Util.generateSha1(concatenatedString);
        System.out.println(signature);
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<PropertiesDTO> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                PropertiesDTO.class
        );
        return response.getBody();
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

        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        headers.setContentType(MediaType.APPLICATION_JSON);  

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<MqttDisableResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                MqttDisableResponse.class
        );
        
        return response.getBody();
    }

    public DeviceResponse bindDevice(bindDTO request) throws NoSuchAlgorithmException {
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

       
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        headers.setContentType(MediaType.APPLICATION_JSON); 

      
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

      
        ResponseEntity<DeviceResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                DeviceResponse.class
        );
        
        return response.getBody();
    }

    public DeviceResponse unbindDevice(bindDTO request) throws NoSuchAlgorithmException {
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

       
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        headers.setContentType(MediaType.APPLICATION_JSON); 

      
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

      
        ResponseEntity<DeviceResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                DeviceResponse.class
        );
        
        return response.getBody();
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

       
        HttpHeaders headers = new HttpHeaders();
        headers.add("appid", appId);
        headers.add("timestamp", timestamp);
        headers.add("signature", signature);
        headers.setContentType(MediaType.APPLICATION_JSON); 

      
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

      
        ResponseEntity<SubscribeResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                SubscribeResponse.class
        );
        
        return response.getBody();
    }
}
