package com.iot.server.application.rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.iot.server.common.model.PostTelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.ts.TsKvService;
import com.iot.server.queue.message.QueueMsg;
import com.iot.server.queue.rabbitmq.RabbitMQProducerTemplate;
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
    private final RabbitMQProducerTemplate rabbitProducerTemplate;

    @RabbitListener(queues = "${queue.rabbitmq.telemetry.queue-name}")
    public void postTelemetry(String msg) {
        QueueMsg<PostTelemetryMsg> queueMsg =
                GsonUtils.fromJson(msg, new TypeToken<QueueMsg<PostTelemetryMsg>>(){}.getType());
        log.info("Consume message [{}]", queueMsg);

        CompletableFuture
                .runAsync(() -> tsKvService.saveOrUpdate(queueMsg))
                .thenRunAsync(() ->  rabbitProducerTemplate.send(msg));
    }
}
