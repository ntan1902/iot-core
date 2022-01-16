package com.iot.server.dao.entity.rule_node;

import com.iot.server.common.entity.BaseEntity;
import com.iot.server.common.entity.EntityConstants;
import com.iot.server.dao.dto.RuleNodeDto;
import com.iot.server.dao.entity.rule_chain.RuleChainEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = EntityConstants.RULE_NODE_TABLE_NAME)
public class RuleNodeEntity extends BaseEntity<UUID> {

    @Column(name = EntityConstants.RULE_NODE_CHAIN_ID_PROPERTY)
    private UUID ruleChainId;

    @Column(name = EntityConstants.RULE_NODE_TYPE_PROPERTY)
    private String type;

    @Column(name = EntityConstants.RULE_NODE_NAME_PROPERTY)
    private String name;

    @Column(name = EntityConstants.RULE_NODE_CONFIGURATION_PROPERTY)
    private String configuration;

    @Column(name = EntityConstants.RULE_NODE_ADDITIONAL_INFO_PROPERTY)
    private String additionalInfo;

    public RuleNodeEntity(RuleNodeDto ruleNodeDto) {
        super(ruleNodeDto);
        this.ruleChainId = ruleNodeDto.getRuleChainId();
        this.type = ruleNodeDto.getType();
        this.name = ruleNodeDto.getName();
        this.configuration = ruleNodeDto.getConfiguration();
        this.additionalInfo = ruleNodeDto.getAdditionalInfo();
    }
}