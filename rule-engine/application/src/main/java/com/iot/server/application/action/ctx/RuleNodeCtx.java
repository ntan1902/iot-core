package com.iot.server.application.action.ctx;

import com.iot.server.application.service.EmailService;
import com.iot.server.application.service.RuleNodeJsEngine;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface RuleNodeCtx {
    RabbitTemplate getTelemetryTemplate();

    RabbitTemplate getDebugTemplate();

    RabbitTemplate getAlarmTemplate();

    RuleNodeJsEngine createJsEngine(String script, String... args);

    EmailService getEmailService();
}
