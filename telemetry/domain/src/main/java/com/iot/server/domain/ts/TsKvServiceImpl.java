package com.iot.server.domain.ts;

import com.iot.server.common.enums.MsgType;
import com.iot.server.common.model.BaseReadQuery;
import com.iot.server.common.model.Kv;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.queue.QueueMsg;
import com.iot.server.dao.dto.TsKvDto;
import com.iot.server.dao.entity.latest.TsKvLatestEntity;
import com.iot.server.dao.entity.ts.TsKvEntity;
import com.iot.server.dao.latest.TsKvLatestDao;
import com.iot.server.dao.ts.TsKvDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TsKvServiceImpl implements TsKvService {

    private final TsKvDao tsKvDao;
    private final TsKvLatestDao tsKvLatestDao;

    @Override
    public void saveOrUpdate(QueueMsg<TelemetryMsg> queueMsg) {
        List<TsKvEntity> tsKvEntities = new ArrayList<>();
        List<TsKvLatestEntity> tsKvLatestEntities = new ArrayList<>();

        TelemetryMsg telemetryMsg = queueMsg.getData();
        for (Kv kv : telemetryMsg.getKvs()) {
            TsKvEntity tsKvEntity = new TsKvEntity(kv);
            tsKvEntity.setEntityId(telemetryMsg.getEntityId());
            tsKvEntities.add(tsKvEntity);

            TsKvLatestEntity tsKvLatestEntity = new TsKvLatestEntity(kv);
            tsKvLatestEntity.setEntityId(telemetryMsg.getEntityId());
            tsKvLatestEntities.add(tsKvLatestEntity);
        }

        CompletableFuture.runAsync(() -> tsKvDao.save(tsKvEntities), Executors.newSingleThreadExecutor());
        if (queueMsg.getType().equals(MsgType.SAVE_LATEST_TELEMETRY.name())) {
            CompletableFuture.runAsync(() -> tsKvLatestDao.save(tsKvLatestEntities), Executors.newSingleThreadExecutor());
        }
    }

    @Override
    public List<TsKvDto> findTsKvByEntityId(UUID entityId, BaseReadQuery query) {
        log.trace("{}", entityId);

        List<TsKvEntity> tsKvByEntityId = tsKvDao.findTsKvByEntityId(entityId, query);

        return tsKvByEntityId.stream()
                .map(tsKvEntity -> new TsKvDto(tsKvEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<TsKvDto> findTsKvLatestByEntityId(UUID entityId) {
        log.trace("{}", entityId);

        List<TsKvLatestEntity> tsKvByLatestEntityId = tsKvLatestDao.findTsKvLatestByEntityId(entityId);

        return tsKvByLatestEntityId.stream()
                .map(tsKvEntity -> new TsKvDto(tsKvEntity))
                .collect(Collectors.toList());
    }
}
