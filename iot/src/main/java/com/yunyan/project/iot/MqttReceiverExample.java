package com.yunyan.project.iot;

import java.util.Base64;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MqttReceiverExample {
    public static void main(String[] args) throws MqttException {
        String brokerUrl = "tcp://armadillo.rmq.cloudamqp.com:1883"; 
        String clientId = "receiverClientId";
        String username = "wczsfsbl:wczsfsbl";
        String password = "YDvvS9pNWw49QYs2BOr8fcQpME8jc4FO";
        String topic = "/topic/pub/sun/device";

        // Create MQTT client and set connection options
        MqttClient client = new MqttClient(brokerUrl, clientId, null);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        client.connect(options);

        // Subscribe to the topic
        client.subscribe(topic, new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String messageContent = new String(message.getPayload());
                JsonObject jsonObject = JsonParser.parseString(messageContent).getAsJsonObject();
                JsonObject payload = jsonObject.getAsJsonObject("payload");

                if (payload != null && payload.has("heartbreath")) {
                    String heartbreathBase64 = payload.get("heartbreath").getAsString();

                    // Decode Base64-encoded heartbreath field
                    byte[] heartbreathBytes = Base64.getDecoder().decode(heartbreathBase64);

                    if (heartbreathBytes.length == 16) {
                        // Interpret the byte array
                        int identifier = heartbreathBytes[0] & 0xFF; // Byte 0
                        int breathingValue = heartbreathBytes[1] & 0xFF; // Byte 1
                        int heartRateValue = heartbreathBytes[2] & 0xFF; // Byte 2

                        // Byte 13 - Status Events
                        int statusEvents = heartbreathBytes[13] & 0xFF;
                        int sleepState = (statusEvents >> 6) & 0x03; // Extract bits 6 and 7

                        // Print the decoded values
                        System.out.println("Decoded heartbreath data:");
                        System.out.println("Identifier: " + identifier);
                        System.out.println("Breathing Value: " + breathingValue + " times/minute");
                        System.out.println("Heart Rate Value: " + heartRateValue + " times/minute");
                        System.out.println("Sleep State: " + decodeSleepState(sleepState));
                    } else {
                        System.out.println("Invalid heartbreath length. Expected 16 bytes, but got: " + heartbreathBytes.length);
                    }
                } else {
                    System.out.println("heartbreath field is missing in the payload.");
                }
            }
        });

        System.out.println("Waiting for messages...");
    }

    // Helper method to decode the sleep state
    private static String decodeSleepState(int sleepState) {
        switch (sleepState) {
            case 0b00:
                return "Undefined";
            case 0b01:
                return "Light Sleep";
            case 0b10:
                return "Deep Sleep";
            case 0b11:
                return "Awake";
            default:
                return "Unknown";
        }
    }
}
