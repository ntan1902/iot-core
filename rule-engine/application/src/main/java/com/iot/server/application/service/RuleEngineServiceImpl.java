package com.iot.server.application.service;

import com.iot.server.application.condition.DefaultCondition;
import com.iot.server.application.condition.RelationCondition;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.dao.dto.RelationDto;
import com.iot.server.dao.dto.RuleChainDto;
import com.iot.server.dao.dto.RuleNodeDto;
import com.iot.server.domain.relation.RelationService;
import com.iot.server.domain.rule_chain.RuleChainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RuleEngineServiceImpl implements RuleEngineService {

    private final RuleChainService ruleChainService;
    private final RelationService relationService;

    @Override
    public void process(RuleNodeMsg ruleNodeMsg) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.trace("{}", ruleNodeMsg);

        List<RuleNodeDto> ruleNodes = ruleChainService.findRuleNodesById(ruleNodeMsg.getRuleChainId());
        RuleChainDto ruleChain = ruleChainService.findRuleChainById(ruleNodeMsg.getRuleChainId());

        Map<UUID, RuleNodeDto> ruleNodeMap = new HashMap<>();

        if (ruleNodes != null && !ruleNodes.isEmpty()) {
            ruleNodes.forEach(ruleNode -> ruleNodeMap.put(ruleNode.getId(), ruleNode));

            Rules rules = new Rules();
            registerRule(ruleChain.getFirstRuleNodeId(), "", ruleNodeMap, rules, true);

            List<RelationDto> relations = getRelations(ruleNodes);
            for (RelationDto relation : relations) {
                registerRule(relation.getToId(), relation.getName(), ruleNodeMap, rules, false);
            }

            RulesEngine rulesEngine = new InferenceRulesEngine();
            rulesEngine.fire(rules, new Facts());
        }
    }

    private void registerRule(UUID ruleNodeId, String name, Map<UUID, RuleNodeDto> ruleNodeMap, Rules rules, boolean defaultCondition) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RuleNodeDto ruleNode = ruleNodeMap.get(ruleNodeId);

        Condition condition = defaultCondition ? new DefaultCondition() : new RelationCondition(name);
        Class<?> componentClazz = Class.forName(ruleNode.getClazz());
        Action action = (Action) componentClazz.getDeclaredConstructor().newInstance();

        rules.register(
                new RuleBuilder()
                        .name(ruleNode.getName())
                        .when(condition)
                        .then(action)
                        .build()
        );
    }

    private List<RelationDto> getRelations(List<RuleNodeDto> ruleNodes) {
        return relationService.findRelationsByFromIds(
                ruleNodes.stream()
                        .map(RuleNodeDto::getId)
                        .collect(Collectors.toList())
        );
    }
}
