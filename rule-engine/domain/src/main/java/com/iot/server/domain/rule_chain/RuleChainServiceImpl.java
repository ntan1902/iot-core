package com.iot.server.domain.rule_chain;

import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import com.iot.server.common.model.BaseReadQuery;
import com.iot.server.dao.dto.RuleChainDto;
import com.iot.server.dao.dto.RuleNodeDto;
import com.iot.server.dao.entity.rule_chain.RuleChainEntity;
import com.iot.server.dao.entity.rule_node.RuleNodeEntity;
import com.iot.server.dao.relation.RelationDao;
import com.iot.server.dao.rule_chain.RuleChainDao;
import com.iot.server.dao.rule_node.RuleNodeDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RuleChainServiceImpl implements RuleChainService {

    private final RuleChainDao ruleChainDao;
    private final RelationDao relationDao;
    private final RuleNodeDao ruleNodeDao;

    @Override
    public RuleChainDto findRuleChainById(UUID ruleChainId) {
        log.trace("{}", ruleChainId);
        return new RuleChainDto(ruleChainDao.findById(ruleChainId));
    }

    @Override
    public List<RuleNodeDto> findRuleNodesById(UUID ruleChainId) {
        log.trace("{}", ruleChainId);
        return ruleNodeDao
                .findAllByRuleChainId(ruleChainId)
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

    @Override
    public RuleChainDto createRuleChain(RuleChainDto ruleChainDto) {
        log.trace("{}", ruleChainDto);
        return new RuleChainDto(
                ruleChainDao.save(new RuleChainEntity(ruleChainDto))
        );
    }

    @Override
    public List<RuleNodeDto> updateRuleNodes(UUID ruleChainId, List<RuleNodeEntity> ruleNodeEntities) {
        log.info("{}, {}", ruleChainId, ruleNodeEntities);
        RuleChainEntity ruleChainEntity = ruleChainDao.findById(ruleChainId);

        if (ruleChainEntity == null) {
            log.error("Rule chain is not found {}", ruleChainId);
            throw new IoTException(ReasonEnum.INVALID_PARAMS, "Rule chain is not found");
        }

        List<RuleNodeEntity> foundRuleNodes = ruleNodeDao.findAllByRuleChainId(ruleChainId);
        List<RuleNodeEntity> toDelete = new ArrayList<>();

        deleteOrUpdateFoundRuleNodes(ruleNodeEntities, foundRuleNodes, toDelete);

        for (RuleNodeEntity ruleNode : ruleNodeEntities) {
            if (ruleNode.getId() == null) {
                foundRuleNodes.add(ruleNode);
            }
        }

        for (RuleNodeEntity ruleNode : toDelete) {
            relationDao.deleteRelations(ruleNode.getId());
            foundRuleNodes.remove(ruleNode);
        }

        List<RuleNodeEntity> savedRuleNodes = ruleNodeDao.saveAllAndFlush(foundRuleNodes);

        return savedRuleNodes.stream()
                .map(RuleNodeDto::new)
                .collect(Collectors.toList());
    }

    private void deleteOrUpdateFoundRuleNodes(List<RuleNodeEntity> ruleNodeEntities, List<RuleNodeEntity> foundRuleNodes, List<RuleNodeEntity> toDelete) {
        Map<UUID, Integer> ruleNodeIndexMap = getRuleNodeIndexMap(ruleNodeEntities);
        for (RuleNodeEntity foundRuleNode : foundRuleNodes) {
            Integer index = ruleNodeIndexMap.getOrDefault(foundRuleNode.getId(), null);

            if (index == null) {
                toDelete.add(foundRuleNode);
            } else {
                int i = foundRuleNodes.indexOf(foundRuleNode);
                foundRuleNodes.set(i, ruleNodeEntities.get(index));
            }
        }
    }

    private Map<UUID, Integer> getRuleNodeIndexMap(List<RuleNodeEntity> ruleNodeEntities) {
        Map<UUID, Integer> res = new HashMap<>();

        ruleNodeEntities.forEach(ruleNodeEntity -> {
            if (ruleNodeEntity.getId() != null) {
                res.put(ruleNodeEntity.getId(), ruleNodeEntities.indexOf(ruleNodeEntity));
            }
        });

        return res;
    }
}
