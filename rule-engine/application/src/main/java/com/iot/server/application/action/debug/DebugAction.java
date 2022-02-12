package com.iot.server.application.action.debug;

import com.iot.server.application.action.AbstractRuleNodeAction;
import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.action.ctx.RuleNodeCtx;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.application.service.RuleNodeJsEngine;
import com.iot.server.common.queue.QueueMsg;
import com.iot.server.common.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionException;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "debug",
        configClazz = DebugConfiguration.class
)
public class DebugAction extends AbstractRuleNodeAction {

    private DebugConfiguration config;
    private RuleNodeJsEngine jsEngine;

    @Override
    protected void initConfig(String config) {
        this.config = GsonUtils.fromJson(config, DebugConfiguration.class);
        this.jsEngine = this.ctx.createJsEngine(this.config.getScript());
    }

    @Override
    protected void executeMsg(RuleNodeMsg msg, Set<String> relationNames) {
        jsEngine.executeToStringAsync(msg)
                .thenAccept(result -> {
                    log.info("{}", result);
                    ctx.getDebugTemplate().convertAndSend(
                            GsonUtils.toJson(new QueueMsg(UUID.randomUUID(), msg.getEntityId(), msg.getRuleChainId(), result, msg.getMetaData(), msg.getType(), msg.getUserIds()))
                    );

                    setSuccess(relationNames);
                })
                .exceptionally(t -> {
                    log.error("Error occurred when execute js: ", t);
                    ctx.getDebugTemplate().convertAndSend(
                            GsonUtils.toJson(new QueueMsg(UUID.randomUUID(), msg.getEntityId(), msg.getRuleChainId(), t.getMessage(), msg.getMetaData(), msg.getType(), msg.getUserIds()))
                    );

                    setFailure(relationNames);
                    throw new CompletionException(t);
                });
    }
}
