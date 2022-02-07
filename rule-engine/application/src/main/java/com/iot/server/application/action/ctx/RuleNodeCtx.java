package com.iot.server.application.action.ctx;

import com.iot.server.application.service.RuleNodeJsEngine;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface RuleNodeCtx {
    RabbitTemplate getTelemetryTemplate();

    RabbitTemplate getDebugTemplate();

    RuleNodeJsEngine createJsEngine(String script, String... args);
}
