package com.iot.server.application.rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.application.service.RuleEngineService;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.queue.message.QueueMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerTemplate {

    private final RuleEngineService ruleEngineService;

    @RabbitListener(queues = "${queue.rabbitmq.rule-engine.queue-name}")
    public void postTelemetry(String msg) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        QueueMsg<TelemetryMsg> queueMsg =
                GsonUtils.fromJson(msg, new TypeToken<QueueMsg<TelemetryMsg>>() {
                }.getType());
        log.trace("Consume message {}", queueMsg);

        RuleNodeMsg ruleNodeMsg = getRuleNodeMsg(queueMsg);
        ruleEngineService.process(ruleNodeMsg);
    }

    private RuleNodeMsg getRuleNodeMsg(QueueMsg<TelemetryMsg> queueMsg) {
        return RuleNodeMsg.builder()
                .type(queueMsg.getType())
                .ruleChainId(queueMsg.getData().getRuleChainId())
                .userId(queueMsg.getData().getUserId())
                .tenantId(queueMsg.getData().getTenantId())
                .data(GsonUtils.toJson(queueMsg.getData()))
                .build();
    }
}
