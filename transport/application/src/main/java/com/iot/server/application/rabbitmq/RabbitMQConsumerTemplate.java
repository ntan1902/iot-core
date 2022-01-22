package com.iot.server.application.rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.WebSocketService;
import com.iot.server.queue.message.QueueMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerTemplate {

    private final WebSocketService webSocketService;

    @RabbitListener(queues = "${queue.rabbitmq.telemetry.queue-name}")
    public void postTelemetry(String msg) {
        QueueMsg<TelemetryMsg> queueMsg =
                GsonUtils.fromJson(msg, new TypeToken<QueueMsg<TelemetryMsg>>() {
                }.getType());
        log.info("Consume message {}", queueMsg);

        try {
            TelemetryMsg telemetryMsg = queueMsg.getData();
            webSocketService.send(telemetryMsg);
        } catch (RuntimeException exception) {
            log.error("Error occurred", exception);
            throw new AmqpRejectAndDontRequeueException(exception);
        }

    }

}
