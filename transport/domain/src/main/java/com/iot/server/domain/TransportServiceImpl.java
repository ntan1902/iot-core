package com.iot.server.domain;

import com.iot.server.common.enums.DeviceCredentialsType;
import com.iot.server.common.enums.MsgType;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.model.MetaData;
import com.iot.server.common.queue.QueueMsg;
import com.iot.server.common.request.GetOrCreateDeviceRequest;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.model.GatewayPostTelemetry;
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

        try {
            DeviceResponse deviceResponse = validateAndGetDevice(transportType, validateDeviceToken);
            sendToRuleEngine(json, deviceResponse);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void sendToRuleEngine(String json, DeviceResponse deviceResponse) {
        MetaData metaData = new MetaData();
        metaData.putValue("deviceName", deviceResponse.getName());
        metaData.putValue("deviceLabel", deviceResponse.getLabel());
        metaData.putValue("deviceType", deviceResponse.getType());

        ruleEngineRabbitTemplate.convertAndSend(
                GsonUtils.toJson(new QueueMsg(UUID.randomUUID(), deviceResponse.getId(), deviceResponse.getRuleChainId(), json, metaData, MsgType.POST_TELEMETRY_REQUEST.name(), deviceResponse.getUserIds()))
        );
    }

    @Override
    public void gwProcess(TransportType transportType, ValidateDeviceToken validateDeviceToken, String json) {
        log.trace("{}, {}, {}", transportType, validateDeviceToken, json);

        try {
            DeviceResponse gwDeviceResponse = validateAndGetDevice(transportType, validateDeviceToken);

            GatewayPostTelemetry gwPostTelemetry = GsonUtils.fromJson(json, GatewayPostTelemetry.class);

            DeviceResponse deviceResponse = getOrCreateDevice(gwDeviceResponse, gwPostTelemetry);

            sendToRuleEngine(GsonUtils.toJson(gwPostTelemetry.getTelemetry()), deviceResponse);

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
            log.error("Device token is not valid {}", validateDeviceToken.getToken());
            throw new IllegalArgumentException("Device token is not valid");
        }
        return deviceResponse;
    }

    private DeviceResponse getOrCreateDevice(DeviceResponse gwDeviceResponse, GatewayPostTelemetry gwPostTelemetry) {
        GetOrCreateDeviceRequest getOrCreateDeviceRequest = new GetOrCreateDeviceRequest();

        getOrCreateDeviceRequest.setName(gwPostTelemetry.getDeviceName());
        getOrCreateDeviceRequest.setLabel(gwPostTelemetry.getDeviceLabel());

        getOrCreateDeviceRequest.setTenantId(gwDeviceResponse.getTenantId());
        getOrCreateDeviceRequest.setFirstTenantId(gwDeviceResponse.getFirstTenantId());

        DeviceResponse deviceResponse = entityServiceClient.getOrCreateDevice(getOrCreateDeviceRequest);
        if (deviceResponse == null) {
            log.error("Failed to get or create new device: {}, {}", gwDeviceResponse, gwPostTelemetry);
            throw new IllegalArgumentException("Failed to get or create new device");
        }
        return deviceResponse;
    }

}
