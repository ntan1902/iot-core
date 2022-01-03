package com.iot.server.application.rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.ts.TsKvService;
import com.iot.server.queue.message.QueueMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerTemplate {

    private final TsKvService tsKvService;

    @RabbitListener(queues = "${queue.rabbitmq.telemetry.queue-name}")
    public void postTelemetry(String msg) {
        QueueMsg<TelemetryMsg> queueMsg =
                GsonUtils.fromJson(msg, new TypeToken<QueueMsg<TelemetryMsg>>() {
                }.getType());
        log.trace("Consume message [{}]", queueMsg);

        CompletableFuture
                .runAsync(() -> tsKvService.saveOrUpdate(queueMsg));
    }
}
