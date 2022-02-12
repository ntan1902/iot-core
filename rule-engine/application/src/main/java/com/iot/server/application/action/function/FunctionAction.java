package com.iot.server.application.action.function;

import com.iot.server.application.action.AbstractRuleNodeAction;
import com.iot.server.application.action.RuleNode;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.application.service.RuleNodeJsEngine;
import com.iot.server.common.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "function",
        configClazz = FunctionConfiguration.class
)
public class FunctionAction extends AbstractRuleNodeAction {

    private FunctionConfiguration config;
    private RuleNodeJsEngine jsEngine;

    @Override
    protected void initConfig(String config) {
        this.config = GsonUtils.fromJson(config, FunctionConfiguration.class);
        this.jsEngine = this.ctx.createJsEngine(this.config.getScript());
    }

    @Override
    protected void executeMsg(RuleNodeMsg msg, Set<String> relationNames) {
        try {
            CompletableFuture<Void> future = jsEngine.executeUpdateAsync(msg);
            future.get();

            setSuccess(relationNames);
        } catch (Exception ex) {
            log.error("Error occurred when execute js: ", ex);
            setFailure(relationNames);
            throw new CompletionException(ex);
        }
    }
}
