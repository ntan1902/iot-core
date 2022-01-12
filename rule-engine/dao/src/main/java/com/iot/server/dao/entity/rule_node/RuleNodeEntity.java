package com.iot.server.dao.entity.rule_node;

import com.iot.server.common.entity.BaseEntity;
import com.iot.server.common.entity.EntityConstants;
import com.iot.server.dao.entity.rule_chain.RuleChainEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = EntityConstants.RULE_NODE_TABLE_NAME)
public class RuleNodeEntity extends BaseEntity<UUID> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = EntityConstants.RULE_NODE_CHAIN_ID_PROPERTY,
            referencedColumnName = EntityConstants.ID_PROPERTY)
    private RuleChainEntity ruleChain;

    @Column(name = EntityConstants.RULE_NODE_TYPE_PROPERTY)
    private String type;

    @Column(name = EntityConstants.RULE_NODE_NAME_PROPERTY)
    private String name;

    @Column(name = EntityConstants.RULE_NODE_CONFIGURATION_PROPERTY)
    private String configuration;

    @Column(name = EntityConstants.RULE_NODE_ADDITIONAL_INFO_PROPERTY)
    private String additionalInfo;
}
