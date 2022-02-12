package com.iot.server.application.condition;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.Set;
import java.util.UUID;

public class RelationCondition implements Condition {
    private final UUID ruleNodeId;
    private final String name;

    public RelationCondition(UUID ruleNodeId, String name) {
        this.ruleNodeId = ruleNodeId;
        this.name = name;
    }

    @Override
    public boolean evaluate(Facts facts) {
        Set<String> relationNames = facts.get(ruleNodeId.toString());
        return relationNames != null && relationNames.contains(name);
    }
}
