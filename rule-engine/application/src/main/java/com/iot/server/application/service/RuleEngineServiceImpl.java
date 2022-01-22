package com.iot.server.application.service;

import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.action.ctx.RuleNodeCtx;
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
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
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
    private final RuleNodeCtx ruleNodeCtx;

    @Override
    public void process(RuleNodeMsg ruleNodeMsg) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.trace("{}", ruleNodeMsg);

        List<RuleNodeDto> ruleNodes = ruleChainService.findRuleNodesById(ruleNodeMsg.getRuleChainId());
        RuleChainDto ruleChain = ruleChainService.findRuleChainById(ruleNodeMsg.getRuleChainId());

        Map<UUID, RuleNodeDto> ruleNodeMap = new HashMap<>();

        if (ruleNodes != null && !ruleNodes.isEmpty()) {
            ruleNodes.forEach(ruleNode -> ruleNodeMap.put(ruleNode.getId(), ruleNode));

            Facts facts = new Facts();
            facts.put("msg", ruleNodeMsg);

            Rules rules = new Rules();
            registerRule(ruleChain.getFirstRuleNodeId(), "", ruleNodeMap, rules, true);

            List<RelationDto> relations = getRelations(ruleNodes);
            for (RelationDto relation : relations) {
                registerRule(relation.getToId(), relation.getName(), ruleNodeMap, rules, false);
            }

            RulesEngine rulesEngine = new DefaultRulesEngine();
            rulesEngine.fire(rules, facts);
        }
    }

    private void registerRule(UUID ruleNodeId,
                              String conditionName,
                              Map<UUID, RuleNodeDto> ruleNodeMap,
                              Rules rules,
                              boolean defaultCondition) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RuleNodeDto ruleNode = ruleNodeMap.get(ruleNodeId);

        Condition condition = defaultCondition ? new DefaultCondition() : new RelationCondition(conditionName);

        RuleNodeAction action = null;
        String name = "";
        if (ruleNode != null) {
            name = ruleNode.getName();

            Class<?> componentClazz = Class.forName(ruleNode.getClazz());
            action = (RuleNodeAction) componentClazz.getDeclaredConstructor().newInstance();
            action.init(ruleNodeCtx, ruleNode.getConfig());
        }
        rules.register(
                new RuleBuilder()
                        .name(name)
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
