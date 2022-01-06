package com.iot.server.domain;

import com.google.gson.JsonParser;
import com.iot.server.common.enums.DeviceCredentialsType;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.model.Kv;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.model.ValidateDeviceToken;
import com.iot.server.queue.QueueProducerTemplate;
import com.iot.server.queue.message.QueueMsg;
import com.iot.server.rest.client.EntityServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final EntityServiceClient entityServiceClient;
    private final QueueProducerTemplate rabbitProducerTemplate;

    @Override
    public void process(TransportType transportType, ValidateDeviceToken validateDeviceToken, String json) {
        log.trace("{}, {}, {}", transportType, validateDeviceToken, json);

        DeviceResponse deviceResponse = validateAndGetDevice(transportType, validateDeviceToken);
        try {
            List<Kv> kvs = GsonUtils.parseJsonElement(JsonParser.parseString(json));
            TelemetryMsg telemetryMsg = TelemetryMsg.builder()
                    .entityId(deviceResponse.getId())
                    .userId(deviceResponse.getUserId())
                    .kvs(kvs)
                    .build();

            rabbitProducerTemplate.send(
                    GsonUtils.toJson(new QueueMsg<>(UUID.randomUUID(), telemetryMsg)));
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
