package com.iot.server.dao.dto;


import com.iot.server.common.dto.BaseDto;
import com.iot.server.dao.entity.rule_node.RuleNodeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RuleNodeDto extends BaseDto<UUID> {
    private UUID ruleChainId;
    private String type;
    private String name;
    private String configuration;
    private String additionalInfo;

    public RuleNodeDto(RuleNodeEntity ruleNodeEntity) {
        super(ruleNodeEntity);
        this.ruleChainId = ruleNodeEntity.getRuleChainId();
        this.type = ruleNodeEntity.getType();
        this.name = ruleNodeEntity.getName();
        this.configuration = ruleNodeEntity.getConfiguration();
        this.additionalInfo = ruleNodeEntity.getAdditionalInfo();
    }
}
