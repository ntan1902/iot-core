package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.GetRuleNodesByRuleChainIdRequest;
import com.iot.server.application.controller.response.GetRuleNodesByRuleChainIdResponse;
import com.iot.server.common.exception.IoTException;
import com.iot.server.dao.dto.RelationDto;
import com.iot.server.dao.dto.RuleNodeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GetRuleNodesByRuleChainIdHandler extends BaseHandler<GetRuleNodesByRuleChainIdRequest, GetRuleNodesByRuleChainIdResponse> {
    @Override
    protected void validate(GetRuleNodesByRuleChainIdRequest request) throws IoTException {
        validateNotNull("ruleChainId", request.getRuleChainId());
        validateNotEmpty("ruleChainId", request.getRuleChainId());
    }

    @Override
    protected GetRuleNodesByRuleChainIdResponse processRequest(GetRuleNodesByRuleChainIdRequest request) {
        GetRuleNodesByRuleChainIdResponse response = new GetRuleNodesByRuleChainIdResponse();

        List<RuleNodeDto> ruleNodes = ruleChainService.findRuleNodesById(toUUID(request.getRuleChainId()));

        if (ruleNodes != null && !ruleNodes.isEmpty()) {
            response.setRuleNodes(ruleNodes);

            List<UUID> fromIds = ruleNodes.stream().map(RuleNodeDto::getId).collect(Collectors.toList());
            List<RelationDto> relations = relationService.findRelationsByFromIds(fromIds);

            if (relations != null && !relations.isEmpty()) {
                response.setRelations(relations);
            }
        }

        return response;
    }
}
