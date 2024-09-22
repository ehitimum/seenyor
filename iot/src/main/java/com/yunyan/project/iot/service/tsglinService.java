package com.yunyan.project.iot.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class tsglinService {

    private String appId = "ql320230911123015310a";
    private String secret = "e6accd9ccabac94453c91f29d8638253d0f36a1a";
    private LocalDateTime timestamp = LocalDateTime.now();
    private Map<String, Object> data = new TreeMap<>();

    private String generateKey(String aapId, String secret, LocalDateTime timestamp, Map<String, Object> data) throws NoSuchAlgorithmException{
        StringBuilder sb = new StringBuilder();
        sb.append(secret).append("#").append(timestamp).append("#");

        // If data is not empty, sort and concatenate it
        if (!data.isEmpty()) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof String[]) {
                    String[] array = (String[]) value;
                    for (String val : array) {
                        sb.append(key).append("=").append(val).append("#");
                    }
                } else {
                    sb.append(key).append("=").append(value.toString()).append("#");
                }
            }

            // Remove the trailing "#" character if there was any data to process
            sb.setLength(sb.length() - 1);
        }

        // Generate the SHA-1 hash
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = digest.digest(sb.toString().getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes).toUpperCase();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public void getDeviceInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
       
    }

}
