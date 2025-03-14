package com.yunyan.project.iot;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.JsonObject;

public class MqttReceiverExample {
    public static void main(String[] args) {
        // MQTT Broker Connection Details
        String brokerUrl = "tcp://whale.rmq.cloudamqp.com:1883"; // Replace with your broker URL
        String clientId = "ehitimum"; // Client ID must be unique
        String username = "zdmihekb:zdmihekb"; // Replace with your MQTT username
        String password = "HVAjAyzbCR1WaaSuAHlCxy8k5Yu7YNhJ"; // Replace with your MQTT password
        String topic = "/topic/pub/sun/device"; // Replace with the topic to subscribe to

        try {
            // Create MQTT client
            MqttClient client = new MqttClient(brokerUrl, clientId, null);

            // Configure connection options
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setCleanSession(true); // Start a clean session
            options.setKeepAliveInterval(60); // Keep alive interval in seconds

            // Set MQTT callback for handling events
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // Parse and print the message in JSON format
                    String messageContent = new String(message.getPayload());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("topic", topic);
                    jsonObject.addProperty("message", messageContent);
                    System.out.println("Received Message in JSON Format: " + jsonObject.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used for subscription
                }
            });

            // Connect to the MQTT broker
            System.out.println("Connecting to broker...");
            client.connect(options);
            System.out.println("Connected successfully!");

            // Subscribe to the specified topic
            System.out.println("Subscribing to topic: " + topic);
            client.subscribe(topic, 1); // QoS level 1
            System.out.println("Waiting for messages...");

            // Keep the program running indefinitely
            synchronized (MqttReceiverExample.class) {
                MqttReceiverExample.class.wait();
            }

        } catch (MqttException e) {
            System.err.println("An error occurred while connecting to the MQTT broker: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Program interrupted: " + e.getMessage());
        }
    }
}




// package com.yunyan.project.iot;

// import java.util.Base64;

// import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
// import org.eclipse.paho.client.mqttv3.MqttClient;
// import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
// import org.eclipse.paho.client.mqttv3.MqttException;
// import org.eclipse.paho.client.mqttv3.MqttMessage;

// import com.google.gson.JsonObject;
// import com.google.gson.JsonParser;

// public class MqttReceiverExample {
//     public static void main(String[] args) throws MqttException {
//         String brokerUrl = "tcp://armadillo.rmq.cloudamqp.com:1883"; 
//         String clientId = "receiverClientId";
//         String username = "wczsfsbl:wczsfsbl";
//         String password = "YDvvS9pNWw49QYs2BOr8fcQpME8jc4FO";
//         String topic = "/topic/pub/sun/device1";

//         // Create MQTT client and set connection options
//         MqttClient client = new MqttClient(brokerUrl, clientId, null);
//         MqttConnectOptions options = new MqttConnectOptions();
//         options.setUserName(username);
//         options.setPassword(password.toCharArray());
//         client.connect(options);

//         // Subscribe to the topic
//         client.subscribe(topic, new IMqttMessageListener() {
//             @Override
//             public void messageArrived(String topic, MqttMessage message) throws Exception {
//                 String messageContent = new String(message.getPayload());
//                 JsonObject jsonObject = JsonParser.parseString(messageContent).getAsJsonObject();
//                 JsonObject payload = jsonObject.getAsJsonObject("payload");

//                 if (payload != null) {
//                     // Decode heartbreath if available
//                     if (payload.has("heartbreath")) {
//                         decodeHeartbreath(payload.get("heartbreath").getAsString());
//                     }

//                     // Decode position if available
//                     if (payload.has("position")) {
//                         decodePosition(payload.get("position").getAsString());
//                     }
//                 } else {
//                     System.out.println("Payload is missing in the message.");
//                 }
//             }
//         });

//         System.out.println("Waiting for messages...");
//     }

//     // Decode the heartbreath field
//     private static void decodeHeartbreath(String heartbreathBase64) {
//         byte[] heartbreathBytes = Base64.getDecoder().decode(heartbreathBase64);

//         if (heartbreathBytes.length == 16) {
//             int identifier = heartbreathBytes[0] & 0xFF;
//             int breathingValue = heartbreathBytes[1] & 0xFF;
//             int heartRateValue = heartbreathBytes[2] & 0xFF;

//             int statusEvents = heartbreathBytes[13] & 0xFF;
//             int sleepState = (statusEvents >> 6) & 0x03;

//             System.out.println("Decoded heartbreath data:");
//             System.out.println("Identifier: " + identifier);
//             System.out.println("Breathing Value: " + breathingValue + " times/minute");
//             System.out.println("Heart Rate Value: " + heartRateValue + " times/minute");
//             System.out.println("Sleep State: " + decodeSleepState(sleepState));
//         } else {
//             System.out.println("Invalid heartbreath length. Expected 16 bytes.");
//         }
//     }

//     // Decode the position field
//     private static void decodePosition(String positionBase64) {
//         byte[] positionBytes = Base64.getDecoder().decode(positionBase64);

//         if (positionBytes.length % 16 != 0) {
//             System.out.println("Invalid position length. Must be a multiple of 16 bytes.");
//             return;
//         }

//         int numPeople = positionBytes.length / 16;

//         System.out.println("Decoded position data for " + numPeople + " people:");
//         for (int i = 0; i < numPeople; i++) {
//             int offset = i * 16;
//             int targetId = positionBytes[offset] & 0xFF;
//             int xCoordinate = positionBytes[offset + 1];
//             int yCoordinate = positionBytes[offset + 2];
//             int zCoordinate = positionBytes[offset + 3] & 0xFF;

//             int timeLeft = positionBytes[offset + 12] & 0xFF;
//             int posture = positionBytes[offset + 13] & 0xFF;
//             int event = positionBytes[offset + 14] & 0xFF;
//             int areaId = positionBytes[offset + 15] & 0xFF;

//             System.out.println("Person " + (i + 1) + ":");
//             System.out.println("  Target ID: " + targetId);
//             System.out.println("  Coordinates (x, y, z): (" + xCoordinate + " dm, " + yCoordinate + " dm, " + zCoordinate + " cm)");
//             System.out.println("  Time Left: " + timeLeft + " seconds");
//             System.out.println("  Posture: " + decodePosture(posture));
//             System.out.println("  Event: " + decodeEvent(event));
//             if (event == 3 || event == 4) {
//                 System.out.println("  Area ID: " + areaId);
//             }
//         }
//     }

//     // Helper method to decode the sleep state
//     private static String decodeSleepState(int sleepState) {
//         switch (sleepState) {
//             case 0b00:
//                 return "Undefined";
//             case 0b01:
//                 return "Light Sleep";
//             case 0b10:
//                 return "Deep Sleep";
//             case 0b11:
//                 return "Awake";
//             default:
//                 return "Unknown";
//         }
//     }

//     // Helper method to decode the posture
//     private static String decodePosture(int posture) {
//         switch (posture) {
//             case 0:
//                 return "Initialization";
//             case 1:
//                 return "Walking";
//             case 2:
//                 return "Suspected Fall";
//             case 3:
//                 return "Squatting";
//             case 4:
//                 return "Standing";
//             case 5:
//                 return "Fall Confirmation";
//             case 6:
//                 return "In Bed";
//             default:
//                 return "Unknown Posture";
//         }
//     }

//     // Helper method to decode the event
//     private static String decodeEvent(int event) {
//         switch (event) {
//             case 0:
//                 return "No Event";
//             case 1:
//                 return "Enter Room";
//             case 2:
//                 return "Leave Room";
//             case 3:
//                 return "Enter Area";
//             case 4:
//                 return "Leave Area";
//             default:
//                 return "Unknown Event";
//         }
//     }
// }
