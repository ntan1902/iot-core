/**
 * Copyright © 2016-2021 The Thingsboard Authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iot.server.dao.entity;

import com.iot.server.common.entity.BaseEntity;
import com.iot.server.common.entity.EntityConstants;
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
