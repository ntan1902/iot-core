package com.iot.server.application.controller.response;

import com.iot.server.dao.dto.RelationDto;
import com.iot.server.dao.dto.RuleNodeDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetRuleNodesByRuleChainIdResponse {
    private List<RelationDto> relations;
    private List<RuleNodeDto> ruleNodes;
}
