package com.iot.server.domain.rule_chain;

import com.iot.server.dao.dto.RuleChainDto;

import java.util.List;
import java.util.UUID;

public interface RuleChainService {
    RuleChainDto findRuleChainById(UUID ruleChainId);
}
