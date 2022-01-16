package com.iot.server.application.rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.iot.server.common.model.Kv;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.queue.QueueProducerTemplate;
import com.iot.server.queue.message.QueueMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerTemplate {

    private final QueueProducerTemplate rabbitProducerTemplate;

    @RabbitListener(queues = "${queue.rabbitmq.rule-engine.queue-name}")
    public void postTelemetry(String msg) {
        QueueMsg<TelemetryMsg> queueMsg =
                GsonUtils.fromJson(msg, new TypeToken<QueueMsg<TelemetryMsg>>() {
                }.getType());
        log.trace("Consume message {}", queueMsg);

        TelemetryMsg telemetryMsg = queueMsg.getData();
        for (Kv kv : telemetryMsg.getKvs()) {
            Double temperature = (Double) kv.getValue();

            if (temperature > 35) {
                rabbitProducerTemplate.send("Hot");
            }
        }
    }
}
