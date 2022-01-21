package com.iot.server.application.condition;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

public class RelationCondition implements Condition {
    private final String name;

    @Override
    public boolean evaluate(Facts facts) {
        String relationName = facts.get("relationName");
        return name.equals(relationName);
    }

    public RelationCondition(String name) {
        this.name = name;
    }
}
