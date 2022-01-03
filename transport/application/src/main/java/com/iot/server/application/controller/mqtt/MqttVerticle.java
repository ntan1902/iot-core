package com.iot.server.application.controller.mqtt;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.messages.MqttPublishMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MqttVerticle extends AbstractVerticle {

    private static final String DEVICE_TELEMETRY = "/v1/device/telemetry";
    private final MqttController mqttController;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);

        MqttServer mqttServer = MqttServer.create(vertx);
        mqttServer
                .endpointHandler(endpoint -> {
                    log.trace("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());
                    // accept connection from the remote client
                    endpoint.accept(false);

                    publishHandler(endpoint);
                })
                .listen(ar -> {
                    if (ar.succeeded()) {
                        log.info("MQTT server is listening on port " + ar.result().actualPort());
                    } else {
                        log.info("Error on starting the server");
                        ar.cause().printStackTrace();
                    }
                });
    }

    private void publishHandler(MqttEndpoint endpoint) {
        endpoint
                .publishHandler(message -> handleQoS(message, endpoint))
                .publishReleaseHandler(endpoint::publishComplete);
    }

    private void handleQoS(MqttPublishMessage message, MqttEndpoint endpoint) {
        if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
            String topicName = message.topicName();

            switch (topicName) {
                case DEVICE_TELEMETRY:
                    mqttController.postTelemetry(message, endpoint);
                    break;
            }

            endpoint.publishAcknowledge(message.messageId());
        } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
            endpoint.publishRelease(message.messageId());
        }
    }
}
