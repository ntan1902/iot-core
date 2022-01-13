package com.iot.server.domain.rule_chain;

import com.iot.server.common.model.BaseReadQuery;
import com.iot.server.dao.dto.RuleChainDto;
import com.iot.server.dao.dto.RuleNodeDto;

import java.util.List;
import java.util.UUID;

public interface RuleChainService {
    RuleChainDto findRuleChainById(UUID ruleChainId);

    List<RuleNodeDto> findRuleNodesById(UUID ruleChainId);

    List<RuleChainDto> findAllByTenantId(UUID tenantId, BaseReadQuery query);
}
