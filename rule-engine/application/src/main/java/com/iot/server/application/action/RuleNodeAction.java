package com.iot.server.application.action;

import com.iot.server.application.action.ctx.RuleNodeCtx;
import com.iot.server.application.message.RuleNodeMsg;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

public interface RuleNodeAction extends Action {

    void init(RuleNodeCtx ctx, String config);

    default void setMsg(Facts facts, String key, Object value) {
        facts.put(key, value);
    }

    default RuleNodeMsg getMsg(Facts facts, String key) {
        return facts.get(key);
    }

    default void setSuccess(Facts facts) {
        setMsg(facts, "relationName", "Success");
    }

    default void setFailure(Facts facts) {
        setMsg(facts, "relationName", "Failure");
    }
}
