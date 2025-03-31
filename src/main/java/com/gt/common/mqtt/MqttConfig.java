package com.gt.common.mqtt;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于连接MQTT客户端，发送MQTT消息到模拟设备
 *
 * @author guitao
 * @since 2024.06.24
 */
@Configuration
@Slf4j
public class MqttConfig {

    @Value("${mqtt.url}")
    private String url;


    private MqttAsyncClient mqttAsyncClient;


    @Bean
    public MqttAsyncClient initMqttClient() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            // 当未设置标识时随机一个
            String clientId = "gt-server" + System.currentTimeMillis();
            mqttAsyncClient = new MqttAsyncClient(url, clientId, persistence);
            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 保留会话
            connOpts.setCleanSession(true);
            // 设置心跳秒数
            connOpts.setKeepAliveInterval(60);
            connOpts.setConnectionTimeout(60);
            connOpts.setUserName("admin");
            connOpts.setPassword("public".toCharArray());
            connOpts.setAutomaticReconnect(true);
            // 设置回调
            // mqttAsyncClient.setCallback(new MqttReconnectCallback());
            mqttAsyncClient.connect(connOpts);
            log.info("MQTT broke接入成功");
            return mqttAsyncClient;
        } catch (MqttException e) {
            log.error("MQTT broke接入失败", e);
        }
        return null;
    }


    class MqttReconnectCallback implements MqttCallbackExtended {

        @Override
        public void connectComplete(boolean b, String s) {
            String topic = "/+/+/properties/callback/xxj";
            String topic1 = "/+/+/properties/callback/hkmj";
            try {
                mqttAsyncClient.subscribe(topic,1);
                log.info("订阅主题成功");
            } catch (MqttException e) {
                log.error("订阅主题失败", e);
            }
        }

        @Override
        public void connectionLost(Throwable throwable) {
            try {
                mqttAsyncClient.reconnect();
                log.info("MQTT断开连接,重连成功");
            } catch (MqttException e) {
                log.error("MQTT断开连接", e);
            }
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            try {

            } catch (Exception e) {
                log.error("MQTT接收信息", e);
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }
    }

}
