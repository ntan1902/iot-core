package com.iot.server.application.condition;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

public class DefaultCondition implements Condition {
    public DefaultCondition() {
    }

    @Override
    public boolean evaluate(Facts facts) {
        return true;
    }
}
