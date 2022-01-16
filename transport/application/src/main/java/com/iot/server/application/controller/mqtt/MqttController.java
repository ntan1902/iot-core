package com.iot.server.application.controller.mqtt;

import com.iot.server.application.controller.request.PostTelemetryRequest;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.TransportService;
import com.iot.server.domain.model.BasicMqttCredentials;
import com.iot.server.domain.model.ValidateDeviceToken;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPublishMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class MqttController {

    private final TransportService transportService;

    public void postTelemetry(MqttPublishMessage message, MqttEndpoint endpoint) {
        log.trace("Received message {} from MQTT Client {} with QoS {}", message.payload().toString(Charset.defaultCharset()), endpoint.clientIdentifier(), message.qosLevel());

        try {
            if (endpoint.auth() != null) {
                BasicMqttCredentials basicMqttCredentials = new BasicMqttCredentials();
                basicMqttCredentials.setClientId(endpoint.clientIdentifier());
                log.trace("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
                basicMqttCredentials.setUsername(endpoint.auth().getUsername());
                basicMqttCredentials.setPassword(endpoint.auth().getPassword());

                transportService.process(
                        TransportType.MQTT,
                        ValidateDeviceToken.builder().basicMqttCredentials(basicMqttCredentials).build(),
                        message.payload().toString(Charset.defaultCharset())
                );
            } else {
                transportService.process(
                        TransportType.DEFAULT,
                        ValidateDeviceToken.builder().token(endpoint.clientIdentifier()).build(),
                        message.payload().toString(Charset.defaultCharset())
                );
            }
        } catch (RuntimeException ex) {
            endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED);
        }

    }

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
                        request.getJson().toString()
                );
            } else {
                transportService.process(
                        TransportType.DEFAULT,
                        ValidateDeviceToken.builder().token((String) request.getToken()).build(),
                        request.getJson().toString()
                );
            }
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Bad credentials mqtt");
        }

    }
}
