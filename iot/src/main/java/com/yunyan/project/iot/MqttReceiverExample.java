package com.yunyan.project.iot;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.JsonObject;

public class MqttReceiverExample {
    public static void main(String[] args) throws MqttException {
        String brokerUrl = "tcp://armadillo.rmq.cloudamqp.com:1883"; 
        String clientId = "receiverClientId";
        String username = "wczsfsbl:wczsfsbl";
        String password = "YDvvS9pNWw49QYs2BOr8fcQpME8jc4FO";
        String topic = "/topic/pub/sun/device";

        MqttClient client = new MqttClient(brokerUrl, clientId, null);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        client.connect(options);

        client.subscribe(topic, new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String messageContent = new String(message.getPayload());
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("topic", topic);
                jsonObject.addProperty("message", messageContent);
                System.out.println("Received Message in JSON Format: " + jsonObject.toString());
            }
        });
        System.out.println("Waiting for messages...");
    }
}

