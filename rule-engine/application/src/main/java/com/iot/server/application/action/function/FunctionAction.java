package com.iot.server.application.action.function;

import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.action.ctx.RuleNodeCtx;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.application.service.RuleNodeJsEngine;
import com.iot.server.common.queue.QueueMsg;
import com.iot.server.common.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;

import java.util.UUID;
import java.util.concurrent.CompletionException;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "function",
        configClazz = FunctionConfiguration.class
)
public class FunctionAction implements RuleNodeAction {

    private FunctionConfiguration config;
    private RuleNodeCtx ctx;
    private RuleNodeJsEngine jsEngine;

    @Override
    public void init(RuleNodeCtx ctx, String config) {
        this.ctx = ctx;
        this.config = GsonUtils.fromJson(config, FunctionConfiguration.class);
        this.jsEngine = this.ctx.createJsEngine(this.config.getScript());
    }

    @Override
    public void execute(Facts facts) throws Exception {
        RuleNodeMsg msg = getMsg(facts, "msg");

        try {
            jsEngine.executeUpdate(msg);
            setSuccess(facts);
        } catch (Exception ex) {
            log.error("Error occurred when execute js: ", ex);
            ctx.getDebugTemplate().convertAndSend(
                    GsonUtils.toJson(new QueueMsg(UUID.randomUUID(), msg.getEntityId(), msg.getRuleChainId(), ex.getMessage(), msg.getMetaData(), msg.getType(), msg.getUserIds()))
            );

            setFailure(facts);
            throw new CompletionException(ex);
        }
    }
}
