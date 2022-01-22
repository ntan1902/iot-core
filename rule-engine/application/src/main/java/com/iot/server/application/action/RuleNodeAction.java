package com.iot.server.application.action;

import com.iot.server.application.action.ctx.RuleNodeCtx;
import org.jeasy.rules.api.Action;

public interface RuleNodeAction extends Action {
    void init(RuleNodeCtx ctx, String config);
}
