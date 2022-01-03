package com.iot.server.application.controller.mqtt;

import com.iot.server.domain.TransportService;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPublishMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
@Component
public class MqttController {

    private final TransportService transportService;

    public void postTelemetry(MqttPublishMessage message, MqttEndpoint endpoint) {
        log.trace("Received message [{}] with QoS [{}]", message.payload().toString(Charset.defaultCharset()), message.qosLevel());
        if (endpoint.auth() != null) {
            log.trace("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
        }
    }
}
