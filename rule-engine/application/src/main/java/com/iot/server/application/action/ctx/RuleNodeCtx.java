package com.iot.server.application.action.ctx;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface RuleNodeCtx {
    RabbitTemplate getTelemetryTemplate();
}
