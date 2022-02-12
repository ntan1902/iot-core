package com.iot.server.application.action;

import com.iot.server.application.action.ctx.RuleNodeCtx;
import org.jeasy.rules.api.Action;

import java.util.Set;
import java.util.UUID;

public interface RuleNodeAction extends Action {

    void init(RuleNodeCtx ctx, UUID ruleNodeId, String config);


}
