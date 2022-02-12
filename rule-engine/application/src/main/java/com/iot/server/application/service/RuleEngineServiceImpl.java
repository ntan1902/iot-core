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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
            facts.put("relationNames", new HashSet<String>());

            AtomicInteger priority = new AtomicInteger(1);
            Rules rules = new Rules();
            registerRule(null, ruleNodeMap.get(ruleChain.getFirstRuleNodeId()), "", rules, true, priority.getAndIncrement());

            List<RelationDto> relations = getRelations(ruleNodes);

            UUID ruleNodeFromId = relations.get(0).getFromId();
            for (RelationDto relation : relations) {
                int priorityInt;
                if (ruleNodeFromId.equals(relation.getFromId())) {
                    priorityInt = priority.get();
                } else {
                    priorityInt = priority.incrementAndGet();
                    ruleNodeFromId = relation.getFromId();
                }

                registerRule(ruleNodeMap.get(relation.getFromId()), ruleNodeMap.get(relation.getToId()), relation.getName(), rules, false, priorityInt);
            }

            RulesEngine rulesEngine = new DefaultRulesEngine();
            rulesEngine.fire(rules, facts);
        }
    }

    private void registerRule(RuleNodeDto prevRuleNode,
                              RuleNodeDto ruleNode,
                              String conditionName,
                              Rules rules,
                              boolean defaultCondition,
                              int priority) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Condition condition;
        if (defaultCondition) {
            condition = new DefaultCondition();
        } else {
            condition = new RelationCondition(prevRuleNode.getId(), conditionName);
        }

        RuleNodeAction action = null;
        String name = "";
        if (ruleNode != null) {
            name = ruleNode.getName();

            Class<?> componentClazz = Class.forName(ruleNode.getClazz());
            action = (RuleNodeAction) componentClazz.getDeclaredConstructor().newInstance();
            action.init(ruleNodeCtx, ruleNode.getId(), ruleNode.getConfig());
        }
        rules.register(
                new RuleBuilder()
                        .name(name)
                        .when(condition)
                        .then(action)
                        .priority(priority)
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
