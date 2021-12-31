package com.iot.server.dao.ts;

import com.iot.server.common.enums.KvType;
import com.iot.server.dao.entity.TsKvCompositeKey;
import com.iot.server.dao.entity.TsKvEntity;
import com.iot.server.dao.repository.TsKvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

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
//        tsKvRepository.saveAll(tsKvEntities);

        List<SqlParameterSource> args = new ArrayList<>();

        int count = 0;
        for (TsKvEntity tsKvEntity : tsKvEntities) {
            MapSqlParameterSource source = new MapSqlParameterSource()
                    .addValue("entityId", tsKvEntity.getEntityId())
                    .addValue("key", tsKvEntity.getKey())
                    .addValue("ts", tsKvEntity.getTs());


            if (tsKvEntity.getBoolV() != null) {
                source.addValue("boolV", tsKvEntity.getBoolV());
                source.addValue("type", KvType.BOOLEAN.name());
            } else {
                source.addValue("boolV", null);
            }

            if (tsKvEntity.getStringV() != null) {
                source.addValue("stringV", tsKvEntity.getStringV());
                source.addValue("type", KvType.STRING.name());
            } else {
                source.addValue("stringV", null);
            }

            if (tsKvEntity.getLongV() != null) {
                source.addValue("longV", tsKvEntity.getLongV());
                source.addValue("type", KvType.LONG.name());
            } else {
                source.addValue("longV", null);
            }

            if (tsKvEntity.getDoubleV() != null) {
                source.addValue("doubleV", tsKvEntity.getDoubleV());
                source.addValue("type", KvType.DOUBLE.name());
            } else {

                source.addValue("doubleV", null);
            }

            if (tsKvEntity.getJsonV() != null) {
                source.addValue("jsonV", tsKvEntity.getJsonV());
                source.addValue("type", KvType.JSON.name());
            } else {
                source.addValue("jsonV", null);
            }
            args.add(source);

        }
        try {
            jdbcTemplate.batchUpdate(INSERT_ON_CONFLICT_DO_UPDATE, args.toArray(new SqlParameterSource[0]));
        } catch (Exception ex) {
            log.error("Failed to batch update entities ", ex);
        }
    }
}
