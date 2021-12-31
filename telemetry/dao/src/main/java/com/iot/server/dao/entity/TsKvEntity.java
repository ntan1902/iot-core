package com.iot.server.dao.entity;

import com.iot.server.common.entity.EntityConstants;
import com.iot.server.common.enums.KvType;
import com.iot.server.common.model.Kv;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = EntityConstants.TS_KV_TABLE_NAME)
@IdClass(TsKvCompositeKey.class)
public class TsKvEntity {
    @Id
    @Column(name = EntityConstants.TS_KV_ENTITY_ID_PROPERTY, columnDefinition = "uuid")
    protected UUID entityId;

    @Id
    @Column(name = EntityConstants.TS_KV_KEY_PROPERTY)
    protected String key;

    @Id
    @Column(name = EntityConstants.TS_KV_TS_PROPERTY)
    protected Long ts;

    @Column(name = EntityConstants.TS_KV_TYPE_PROPERTY)
    @Enumerated(EnumType.STRING)
    protected KvType type;

    @Column(name = EntityConstants.TS_KV_BOOL_V_PROPERTY)
    protected Boolean boolV;

    @Column(name = EntityConstants.TS_KV_STRING_V_PROPERTY)
    protected String stringV;

    @Column(name = EntityConstants.TS_KV_LONG_V_PROPERTY)
    protected Long longV;

    @Column(name = EntityConstants.TS_KV_DOUBLE_V_PROPERTY)
    protected Double doubleV;

    @Column(name = EntityConstants.TS_KV_JSON_V_PROPERTY)
    protected String jsonV;

    public TsKvEntity(Kv kv) {
        this.type = kv.getType();
        this.key = kv.getKey();

        if (kv.getBoolV() != null) {
            this.boolV = kv.getBoolV();
        }

        if (kv.getDoubleV() != null) {
            this.doubleV = kv.getDoubleV();
        }

        if (kv.getLongV() != null) {
            this.longV = kv.getLongV();
        }

        if (kv.getStringV() != null) {
            this.stringV = kv.getStringV();
        }

        if (kv.getJsonV() != null) {
            this.jsonV = kv.getJsonV();
        }
    }
}
