package com.iot.server.domain;

import com.iot.server.common.enums.DeviceCredentialsType;
import com.iot.server.common.enums.MsgType;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.model.MetaData;
import com.iot.server.common.queue.QueueMsg;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.model.ValidateDeviceToken;
import com.iot.server.rest.client.EntityServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final EntityServiceClient entityServiceClient;
    private final RabbitTemplate ruleEngineRabbitTemplate;

    @Override
    public void process(TransportType transportType, ValidateDeviceToken validateDeviceToken, String json) {
        log.trace("{}, {}, {}", transportType, validateDeviceToken, json);

        DeviceResponse deviceResponse = validateAndGetDevice(transportType, validateDeviceToken);
        try {
            MetaData metaData = new MetaData();
            metaData.putValue("deviceName", deviceResponse.getName());
            metaData.putValue("deviceLabel", deviceResponse.getLabel());
            metaData.putValue("deviceType", deviceResponse.getType());

            ruleEngineRabbitTemplate.convertAndSend(
                    GsonUtils.toJson(new QueueMsg(UUID.randomUUID(), deviceResponse.getId(), deviceResponse.getRuleChainId(), json, metaData, MsgType.POST_TELEMETRY_REQUEST.name(), deviceResponse.getUserIds()))
            );
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private DeviceResponse validateAndGetDevice(TransportType transportType, ValidateDeviceToken validateDeviceToken) {
        ValidateDeviceRequest validateDeviceRequest = new ValidateDeviceRequest();

        if (transportType.equals(TransportType.DEFAULT)) {
            validateDeviceRequest.setType(DeviceCredentialsType.ACCESS_TOKEN);
            validateDeviceRequest.setToken(validateDeviceToken.getToken());
        } else if (transportType.equals(TransportType.MQTT)) {
            validateDeviceRequest.setType(DeviceCredentialsType.MQTT_BASIC);
            validateDeviceRequest.setToken(validateDeviceToken.getBasicMqttCredentials());
        }

        DeviceResponse deviceResponse = entityServiceClient.validateDevice(validateDeviceRequest);
        if (deviceResponse == null) {
            log.warn("Device token is not valid {}", validateDeviceToken.getToken());
            throw new IllegalArgumentException("Device token is not valid");
        }
        return deviceResponse;
    }
}
