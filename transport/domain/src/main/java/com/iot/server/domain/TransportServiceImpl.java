package com.iot.server.domain;

import com.google.gson.JsonParser;
import com.iot.server.common.model.Kv;
import com.iot.server.domain.model.ValidateDeviceToken;
import com.iot.server.common.enums.DeviceCredentialsType;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.model.PostTelemetryMsg;
import com.iot.server.common.model.TsKvList;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.queue.QueueProducerTemplate;
import com.iot.server.queue.message.DefaultQueueMsg;
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
        log.trace("[{}], [{}], [{}]", transportType, validateDeviceToken, json);
        DeviceResponse deviceResponse = entityServiceClient.validateDevice(ValidateDeviceRequest.builder()
                .token(validateDeviceToken.getToken())
                .type(DeviceCredentialsType.ACCESS_TOKEN)
                .build());
        if (deviceResponse == null) {
            log.warn("Device token is not valid [{}]", validateDeviceToken.getToken());
            throw new IllegalArgumentException("Device token is not valid");
        }
        try {
            List<Kv> kvs = GsonUtils.parseJsonElement(JsonParser.parseString(json));
            PostTelemetryMsg postTelemetryMsg = PostTelemetryMsg.builder()
                    .tsKvList(TsKvList.builder()
                            .entityId(deviceResponse.getId())
                            .kvs(kvs)
                            .ts(System.currentTimeMillis())
                            .build())
                    .build();

            rabbitProducerTemplate.send(
                    GsonUtils.toJson(new DefaultQueueMsg<>(UUID.randomUUID(), postTelemetryMsg)));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
