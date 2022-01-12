package com.iot.server.dao.entity.rule_chain;

import com.iot.server.common.entity.BaseEntity;
import com.iot.server.common.entity.EntityConstants;
import com.iot.server.dao.dto.RuleChainDto;
import com.iot.server.dao.entity.rule_node.RuleNodeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = EntityConstants.RULE_CHAIN_TABLE_NAME)
public class RuleChainEntity extends BaseEntity<UUID> {

    @Column(name = EntityConstants.RULE_CHAIN_TENANT_ID_PROPERTY)
    private UUID tenantId;

    @Column(name = EntityConstants.RULE_CHAIN_NAME_PROPERTY)
    private String name;

    @Column(name = EntityConstants.RULE_CHAIN_FIRST_RULE_NODE_ID_PROPERTY)
    private UUID firstRuleNodeId;

    @Column(name = EntityConstants.RULE_CHAIN_ROOT_PROPERTY)
    private Boolean root;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ruleChain")
    @ToString.Exclude
    private Set<RuleNodeEntity> ruleNodes;

    public RuleChainEntity(RuleChainDto ruleChainDto) {
        super(ruleChainDto);
        this.tenantId = ruleChainDto.getTenantId();
        this.name = ruleChainDto.getName();
        this.firstRuleNodeId = ruleChainDto.getFirstRuleNodeId();
        this.root = ruleChainDto.getRoot();
    }
}
