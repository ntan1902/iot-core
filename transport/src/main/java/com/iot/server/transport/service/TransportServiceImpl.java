package com.iot.server.transport.service;

import com.google.gson.JsonParser;
import com.iot.server.common.enums.DeviceCredentialsType;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.model.TsKvList;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.queue.QueueProducerTemplate;
import com.iot.server.queue.message.DefaultQueueMsg;
import com.iot.server.rest.client.EntityServiceClient;
import com.iot.server.transport.dto.PostTelemetryMsg;
import com.iot.server.transport.dto.ValidateDeviceToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final EntityServiceClient entityServiceClient;
    private final QueueProducerTemplate<DefaultQueueMsg<?>> rabbitTemplate;

    @Override
    public void process(TransportType transportType, ValidateDeviceToken validateDeviceToken, String json) {
        log.trace("[{}], [{}], [{}]", transportType, validateDeviceToken, json);
        DeviceResponse deviceResponse = entityServiceClient.validateDevice(ValidateDeviceRequest.builder()
                .token(validateDeviceToken.getToken())
                .type(DeviceCredentialsType.ACCESS_TOKEN)
                .build());
        if (deviceResponse == null) {
            log.warn("Device token is not valid [{}]", validateDeviceToken.getToken());
            throw new IllegalArgumentException("Device token is not valid");
        }

        PostTelemetryMsg postTelemetryMsg = PostTelemetryMsg.builder()
                .tsKvList(TsKvList.builder()
                        .entityId(deviceResponse.getId())
                        .kv(GsonUtils.parseJsonElement(JsonParser.parseString(json)))
                        .ts(System.currentTimeMillis())
                        .build())
                .build();

        rabbitTemplate.send(new DefaultQueueMsg<>(UUID.randomUUID(), postTelemetryMsg));
    }
}
