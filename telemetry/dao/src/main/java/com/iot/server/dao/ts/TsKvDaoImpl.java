package com.iot.server.dao.ts;

import com.iot.server.common.enums.KvType;
import com.iot.server.dao.entity.TsKvCompositeKey;
import com.iot.server.dao.entity.TsKvEntity;
import com.iot.server.dao.repository.TsKvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TsKvDaoImpl implements TsKvDao {

    private static final String INSERT_ON_CONFLICT_DO_UPDATE =
            "INSERT INTO ts_kv (entity_id, key, ts, type, bool_v, string_v, long_v, double_v, json_v) " +
                    "VALUES (:entityId, :key, :ts, :type, :boolV, :stringV, :longV, :doubleV, cast(:jsonV AS json)) " +
                    "ON CONFLICT (entity_id, key, ts) " +
                    "DO UPDATE SET type = :type, bool_v = :boolV, string_v = :stringV, long_v = :longV, double_v = :doubleV, json_v = cast(:jsonV AS json)";
    private final TsKvRepository tsKvRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<TsKvEntity> findAll() {
        log.debug("Executing");
        return tsKvRepository.findAll();
    }

    @Override
    public TsKvEntity findById(TsKvCompositeKey id) {
        log.debug("{}", id);
        return tsKvRepository.findById(id).orElse(null);
    }

    @Override
    public TsKvEntity save(TsKvEntity entity) {
        log.debug("{}", entity);
        return tsKvRepository.save(entity);
    }

    @Override
    public boolean removeById(TsKvCompositeKey id) {
        log.debug("{}", id);
        tsKvRepository.deleteById(id);
        return !tsKvRepository.existsById(id);
    }

    @Override
    public void save(List<TsKvEntity> tsKvEntities) {
        tsKvRepository.saveAll(tsKvEntities);
//        Map<String, Object>[] params = new HashMap[tsKvEntities.size()];
//
//        int count = 0;
//        for (TsKvEntity tsKvEntity : tsKvEntities) {
//            Map<String, Object> ps = new HashMap<>();
//            ps.put("entityId", tsKvEntity.getEntityId());
//            ps.put("key", tsKvEntity.getKey());
//            ps.put("ts", tsKvEntity.getTs());
//
//            if (tsKvEntity.getBoolV() != null) {
//                ps.put("boolV", tsKvEntity.getBoolV());
//                ps.put("type", KvType.BOOLEAN);
//            } else {
//                ps.put("boolV", Types.BOOLEAN);
//            }
//
//            if (tsKvEntity.getStringV() != null) {
//                ps.put("stringV", tsKvEntity.getStringV());
//                ps.put("type", KvType.STRING);
//            } else {
//                ps.put("stringV", Types.VARCHAR);
//            }
//
//
//            if (tsKvEntity.getLongV() != null) {
//                ps.put("longV", tsKvEntity.getLongV());
//                ps.put("type", KvType.LONG);
//            } else {
//                ps.put("longV", Types.BIGINT);
//            }
//
//            if (tsKvEntity.getDoubleV() != null) {
//                ps.put("doubleV", tsKvEntity.getDoubleV());
//                ps.put("type", KvType.DOUBLE);
//            } else {
//                ps.put("doubleV", Types.DOUBLE);
//            }
//
//            if (tsKvEntity.getJsonV() != null) {
//                ps.put("jsonV", tsKvEntity.getJsonV());
//                ps.put("type", KvType.DOUBLE);
//            } else {
//                ps.put("jsonV", Types.VARCHAR);
//            }
//            params[count++] = ps;
//        }
//        jdbcTemplate.batchUpdate(INSERT_ON_CONFLICT_DO_UPDATE, params);
    }
}
