// package com.yunyan.project.authorization;

// import org.eclipse.paho.client.mqttv3.MqttClient;
// import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
// import org.eclipse.paho.client.mqttv3.MqttMessage;

// public class MqttClientExample {
//     public static void main(String[] args) throws Exception {
//         String brokerUrl = "tcp://armadillo.rmq.cloudamqp.com:1883"; // Replace with your RabbitMQ details

//         String clientId = "user11001";
//         //String topic = "your_mqtt_topic";
//         String username = "wczsfsbl:wczsfsbl";
//         String password = "YDvvS9pNWw49QYs2BOr8fcQpME8jc4FO";

//         MqttClient client = new MqttClient(brokerUrl, clientId, null);     
//         MqttConnectOptions options = new MqttConnectOptions();
//         options.setUserName(username);
//         options.setPassword(password.toCharArray());
//         client.connect(options);
//         client.subscribe("/topic/pub/sun/device");
//         while (true) {
//             try {
//                 MqttMessage message = new MqttMessage("Hello from MQTT client!".getBytes());
//                 client.publish("/topic/pub/sun/device", message);
//                 System.out.println("Message sent: " + new String(message.getPayload()));
//                 Thread.sleep(5000);  
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//                 break;
//             }
//     }
// }
// }
