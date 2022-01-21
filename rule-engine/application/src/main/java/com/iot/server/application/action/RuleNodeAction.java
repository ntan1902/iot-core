package com.iot.server.application.action;

import com.iot.server.application.message.RuleNodeMsg;

public interface RuleNodeAction {
    void init(String configuration);

    void onReceived(RuleNodeMsg msg);
}
