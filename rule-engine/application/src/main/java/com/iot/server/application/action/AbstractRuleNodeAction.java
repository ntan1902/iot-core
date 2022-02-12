package com.iot.server.application.action;

import com.iot.server.application.action.ctx.RuleNodeCtx;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.application.utils.RuleNodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
public abstract class AbstractRuleNodeAction implements RuleNodeAction {
    protected RuleNodeCtx ctx;
    protected UUID ruleNodeId;

    @Override
    public void init(RuleNodeCtx ctx, UUID ruleNodeId, String config) {
        this.ctx = ctx;
        this.ruleNodeId = ruleNodeId;
        initConfig(config);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        log.trace("{}", facts);

        RuleNodeMsg msg = facts.get("msg");

        Set<String> relationNames = new HashSet<>();
        facts.put(ruleNodeId.toString(), relationNames);

        executeMsg(msg, relationNames);
    }

    protected void setSuccess(Set<String> relationNames) {
        relationNames.remove("Failure");
        relationNames.add("Success");
    }

    protected void setFailure(Set<String> relationNames) {
        relationNames.remove("Success");
        relationNames.add("Failure");
    }

    protected void setTrue(Set<String> relationNames) {
        relationNames.remove("False");
        relationNames.add("True");
    }

    protected void setFalse(Set<String> relationNames) {
        relationNames.remove("True");
        relationNames.add("False");
    }

    protected String processTemplate(String template, RuleNodeMsg msg) {
        if (StringUtils.hasText(template)) {
            return RuleNodeUtils.processPattern(template, msg);
        }
        return "";
    }


    protected abstract void initConfig(String config);

    protected abstract void executeMsg(RuleNodeMsg msg, Set<String> relationNames);
}
