package com.iot.server.application.controller.mqtt;

import com.iot.server.application.controller.request.PostTelemetryRequest;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.TransportService;
import com.iot.server.domain.model.BasicMqttCredentials;
import com.iot.server.domain.model.ValidateDeviceToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class MqttController {

    private final TransportService transportService;

    @RabbitListener(queues = "${queue.rabbitmq.mqtt.queue-name}")
    public void postTelemetry(String message) {
        log.trace("Received message {} from MQTT", message);
        try {
            PostTelemetryRequest request = GsonUtils.fromJson(message, PostTelemetryRequest.class);

            if (request.getToken() instanceof BasicMqttCredentials
                    || request.getToken() instanceof Map) {
                BasicMqttCredentials basicMqttCredentials = GsonUtils.fromJson(request.getToken().toString(), BasicMqttCredentials.class);
                log.trace("[username = " + basicMqttCredentials.getUsername() + ", password = " + basicMqttCredentials.getPassword() + "]");

                transportService.process(
                        TransportType.MQTT,
                        ValidateDeviceToken.builder().basicMqttCredentials(basicMqttCredentials).build(),
                        GsonUtils.toJson(request.getJson())
                );
            } else {
                transportService.process(
                        TransportType.DEFAULT,
                        ValidateDeviceToken.builder().token((String) request.getToken()).build(),
                        GsonUtils.toJson(request.getJson())

                );
            }
        } catch (RuntimeException ex) {
            log.error("Bad credentials mqtt ",ex);
        }

    }
}
