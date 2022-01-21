package com.iot.server.dao.entity.ts;

import com.iot.server.common.entity.EntityConstants;
import com.iot.server.common.enums.KvType;
import com.iot.server.common.model.Kv;
import com.iot.server.dao.dto.TsKvDto;
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

    @Column(name = EntityConstants.TS_KV_STRING_V_PROPERTY, length = 10_000)
    protected String stringV;

    @Column(name = EntityConstants.TS_KV_LONG_V_PROPERTY)
    protected Long longV;

    @Column(name = EntityConstants.TS_KV_DOUBLE_V_PROPERTY)
    protected Double doubleV;

    @Column(name = EntityConstants.TS_KV_JSON_V_PROPERTY, length = 10_000_000)
    protected String jsonV;

    public TsKvEntity(Kv kv) {
        this.type = kv.getType();
        this.key = kv.getKey();
        this.ts = kv.getTs();

        switch (kv.getType()) {
            case DOUBLE:
                this.doubleV = (Double) kv.getValue();
                break;
            case LONG:
                this.longV = ((Double) kv.getValue()).longValue();
                break;
            case BOOLEAN:
                this.boolV = (Boolean) kv.getValue();
                break;
            case STRING:
                this.stringV = (String) kv.getValue();
                break;
            case JSON:
                this.jsonV = (String) kv.getValue();
                break;
            default:
                break;
        }
    }

    public TsKvEntity(TsKvDto tsKvDto) {
        this.type = tsKvDto.getType();
        this.key = tsKvDto.getKey();
        this.ts = tsKvDto.getTs();
        this.entityId = tsKvDto.getEntityId();

        switch (tsKvDto.getType()) {
            case DOUBLE:
                this.doubleV = (Double) tsKvDto.getValue();
                break;
            case LONG:
                this.longV = ((Double) tsKvDto.getValue()).longValue();
                break;
            case BOOLEAN:
                this.boolV = (Boolean) tsKvDto.getValue();
                break;
            case STRING:
                this.stringV = (String) tsKvDto.getValue();
                break;
            case JSON:
                this.jsonV = (String) tsKvDto.getValue();
                break;
            default:
                break;
        }
    }
}
