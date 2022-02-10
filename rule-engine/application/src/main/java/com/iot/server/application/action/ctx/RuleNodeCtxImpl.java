package com.iot.server.application.action.ctx;

import com.iot.server.application.service.EmailService;
import com.iot.server.application.service.NashornService;
import com.iot.server.application.service.RuleNodeJsEngine;
import com.iot.server.application.service.RuleNodeJsEngineImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RuleNodeCtxImpl implements RuleNodeCtx {

    private final RabbitTemplate telemetryRabbitTemplate;
    private final RabbitTemplate debugRabbitTemplate;
    private final NashornService nashornService;
    private final EmailService emailService;

    @Override
    public RabbitTemplate getTelemetryTemplate() {
        return telemetryRabbitTemplate;
    }

    @Override
    public RabbitTemplate getDebugTemplate() {
        return debugRabbitTemplate;
    }

    @Override
    public RuleNodeJsEngine createJsEngine(String script, String... args) {
        return new RuleNodeJsEngineImpl(nashornService, script, args);
    }

    @Override
    public EmailService getEmailService() {
        return emailService;
    }
}
