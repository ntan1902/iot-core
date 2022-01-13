package com.iot.server.domain.rule_chain;

import com.iot.server.common.model.BaseReadQuery;
import com.iot.server.dao.dto.RuleChainDto;
import com.iot.server.dao.dto.RuleNodeDto;
import com.iot.server.dao.entity.rule_chain.RuleChainEntity;
import com.iot.server.dao.rule_chain.RuleChainDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RuleChainServiceImpl implements RuleChainService {

    private final RuleChainDao ruleChainDao;

    @Override
    public RuleChainDto findRuleChainById(UUID ruleChainId) {
        log.trace("{}", ruleChainId);
        return new RuleChainDto(ruleChainDao.findById(ruleChainId));
    }

    @Override
    @Transactional
    public List<RuleNodeDto> findRuleNodesById(UUID ruleChainId) {
        log.trace("{}", ruleChainId);
        return ruleChainDao
                .findById(ruleChainId)
                .getRuleNodes()
                .stream()
                .map(ruleNodeEntity -> new RuleNodeDto(ruleNodeEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<RuleChainDto> findAllByTenantId(UUID tenantId, BaseReadQuery query) {
        log.trace("{}, {}", tenantId, query);

        List<RuleChainEntity> ruleChainEntities = ruleChainDao.findAllByTenantId(tenantId, query);

        return ruleChainEntities.stream()
                .map(ruleChainEntity -> new RuleChainDto(ruleChainEntity))
                .collect(Collectors.toList());
    }
}
