package com.gt.common.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.gt.common.exception.IotException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 通过MQTT下发到模拟设备 1用户信息下发 2时间规则下发  3从设备删除用户信息
 *
 * @author guitao
 * @since 2024.06.25
 */
@Component
@Slf4j
public class MqttUtil {

    private static final String CAPEX_HKMJ = "/capex_hkmj/";
    private static final String WRITE = "/properties/write";

    @Autowired
    private MqttAsyncClient mqttAsyncClient;

    /**
     * 用户信息下发设备
     *
     * @param deviceId deviceId
     * @param userInfoDTO userInfoDTO
     */
    public void publishUserInfoToDevice(String deviceId, Object userInfoDTO) {
        doPublish(deviceId, userInfoDTO, "1");
    }

    /**
     * 时间规则下发设备
     *
     * @param hkDeviceRuleDTO hkDeviceRuleDTO
     */
    public void publishTimeRuleToDevice(String deviceId, List<Object> hkDeviceRuleDTO) {
        doPublish(deviceId, hkDeviceRuleDTO, "2");
    }

    /**
     * 从设备删除用户信息
     *
     * @param deviceId deviceId
     * @param userNo userNo
     * @param cardNos cardNos
     */
    public void deleteUserInfoToDevice(String deviceId, String userNo, String[] cardNos) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(userNo)) {
            jsonObject.put("userNo", userNo);
        }
        if (Objects.nonNull(cardNos)) {
            jsonObject.put("cardList", new ArrayList<>(Arrays.asList(cardNos)));
        }
        doPublish(deviceId, jsonObject, "3");
    }

    public void doPublish(String deviceId, Object message, String type) {
        String topic = CAPEX_HKMJ + deviceId + WRITE;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        jsonObject.put("type", type);
        JSONObject payload = new JSONObject();
        payload.put("properties", jsonObject);
        publish(topic, payload.toJSONString());
    }

    /**
     * 消息下发到MQTT broke
     *
     * @param topic topic
     * @param payload payload
     */
    public void publish(String topic, String payload) {
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            mqttAsyncClient.publish(topic, message);
            log.info("Published to topic: {}, message: {}", topic, payload);
        } catch (Exception e) {
            log.error("Failed to publish MQTT message", e);
            throw new IotException(500, "Failed to publish MQTT message");
        }
    }
}
