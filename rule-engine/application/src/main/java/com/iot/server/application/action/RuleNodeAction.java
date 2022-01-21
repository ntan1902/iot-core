package com.iot.server.application.action;

import com.iot.server.application.message.RuleNodeMsg;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

public interface RuleNodeAction extends Action {
    void init(String config);
}
