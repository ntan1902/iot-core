package com.iot.server.dao.entity.relation;

import com.iot.server.common.entity.EntityConstants;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = EntityConstants.RELATION_TABLE_NAME)
@IdClass(RelationCompositeKey.class)
public final class RelationEntity {

    @Id
    @Column(name = EntityConstants.RELATION_FROM_ID_PROPERTY, columnDefinition = "uuid")
    private UUID fromId;

    @Id
    @Column(name = EntityConstants.RELATION_FROM_TYPE_PROPERTY)
    private String fromType;

    @Id
    @Column(name = EntityConstants.RELATION_TO_ID_PROPERTY, columnDefinition = "uuid")
    private UUID toId;

    @Id
    @Column(name = EntityConstants.RELATION_TO_TYPE_PROPERTY)
    private String toType;
}