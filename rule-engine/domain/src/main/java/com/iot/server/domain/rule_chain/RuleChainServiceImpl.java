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
        RuleChainEntity ruleChainEntity = ruleChainDao.findById(ruleChainId);
        if (ruleChainEntity == null) {
            return null;
        }
        return new RuleChainDto(ruleChainEntity);
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
    public List<RuleNodeDto> updateRuleNodes(UUID ruleChainId, Integer firstRuleNodeIndex, List<RuleNodeEntity> ruleNodeEntities) {
        log.info("{}, {}", ruleChainId, ruleNodeEntities);

        RuleChainEntity ruleChainEntity = ruleChainDao.findById(ruleChainId);
        if (ruleChainEntity == null) {
            log.error("Rule chain is not found {}", ruleChainId);
            throw new IoTException(ReasonEnum.INVALID_PARAMS, "Rule chain is not found");
        }

        List<RuleNodeEntity> foundRuleNodes = ruleNodeDao.findAllByRuleChainId(ruleChainId);
        List<RuleNodeEntity> toDelete = new ArrayList<>();

        deleteOrUpdateFoundNodes(ruleNodeEntities, foundRuleNodes, toDelete);

        addNodes(ruleNodeEntities, foundRuleNodes);
        deleteNodes(foundRuleNodes, toDelete);

        if (!foundRuleNodes.isEmpty()) {
            List<RuleNodeEntity> savedRuleNodes = ruleNodeDao.saveAllAndFlush(foundRuleNodes);

            UUID firstRuleNodeId = null;
            if (firstRuleNodeIndex != null && firstRuleNodeIndex >= 0) {
                firstRuleNodeId = savedRuleNodes.get(firstRuleNodeIndex).getId();
            }

            if (firstRuleNodeId != null) {
                ruleChainEntity.setFirstRuleNodeId(firstRuleNodeId);
            }

            return savedRuleNodes.stream()
                    .map(RuleNodeDto::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void deleteOrUpdateFoundNodes(List<RuleNodeEntity> ruleNodeEntities,
                                          List<RuleNodeEntity> foundRuleNodes,
                                          List<RuleNodeEntity> toDelete) {
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

    private void addNodes(List<RuleNodeEntity> ruleNodeEntities, List<RuleNodeEntity> foundRuleNodes) {
        for (RuleNodeEntity ruleNode : ruleNodeEntities) {
            if (ruleNode.getId() == null) {
                foundRuleNodes.add(ruleNode);
            }
        }
    }

    private void deleteNodes(List<RuleNodeEntity> foundRuleNodes, List<RuleNodeEntity> toDelete) {
        for (RuleNodeEntity ruleNode : toDelete) {
            relationDao.deleteRelations(ruleNode.getId());
            ruleNodeDao.removeById(ruleNode.getId());
            foundRuleNodes.remove(ruleNode);
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
