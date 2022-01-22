package com.iot.server.application.action.ctx;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RuleNodeCtxImpl implements RuleNodeCtx {
    private final RabbitTemplate telemetryRabbitTemplate;


    @Override
    public RabbitTemplate getTelemetryTemplate() {
        return telemetryRabbitTemplate;
    }
}
