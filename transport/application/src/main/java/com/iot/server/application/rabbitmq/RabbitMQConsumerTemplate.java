package com.iot.server.application.rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.iot.server.common.model.PostTelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.WebSocketService;
import com.iot.server.queue.message.QueueMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerTemplate {

    private final WebSocketService webSocketService;

    @RabbitListener(queues = "${queue.rabbitmq.telemetry.queue-name}")
    public void notificationTelemetry(String msg) {
        QueueMsg<PostTelemetryMsg> queueMsg =
                GsonUtils.fromJson(msg, new TypeToken<QueueMsg<PostTelemetryMsg>>() {
                }.getType());
        log.info("Consume message [{}]", queueMsg);

        PostTelemetryMsg postTelemetryMsg = queueMsg.getData();
        webSocketService.send(postTelemetryMsg);
    }
}
