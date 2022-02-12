package com.iot.server.application.action.filter;

import com.iot.server.application.action.AbstractRuleNodeAction;
import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.action.ctx.RuleNodeCtx;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.application.service.RuleNodeJsEngine;
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
        name = "filter",
        configClazz = FilterConfiguration.class,
        relationNames = {"True", "False", "Success", "Failure"}
)
public class FilterAction extends AbstractRuleNodeAction {

    private FilterConfiguration config;
    private RuleNodeJsEngine jsEngine;

    @Override
    protected void initConfig(String config) {
        this.config = GsonUtils.fromJson(config, FilterConfiguration.class);
        this.jsEngine = this.ctx.createJsEngine(this.config.getScript());
    }

    @Override
    protected void executeMsg(RuleNodeMsg msg, Set<String> relationNames) {
        try {
            Boolean result = jsEngine.executeFilterAsync(msg).get();

            if (Boolean.TRUE.equals(result)) {
                setTrue(relationNames);
            } else {
                setFalse(relationNames);
            }

            setSuccess(relationNames);
        } catch (Exception ex) {
            log.error("Error occurred when execute js: ", ex);
            setFailure(relationNames);
            throw new CompletionException(ex);
        }
    }
}