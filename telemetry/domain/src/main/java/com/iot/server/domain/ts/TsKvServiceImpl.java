package com.iot.server.domain.ts;

import com.iot.server.common.model.BaseReadQuery;
import com.iot.server.common.model.Kv;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.dao.dto.TsKvDto;
import com.iot.server.dao.entity.TsKvEntity;
import com.iot.server.dao.ts.TsKvDao;
import com.iot.server.queue.message.QueueMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TsKvServiceImpl implements TsKvService {

    private final TsKvDao tsKvDao;

    @Override
    public void saveOrUpdate(QueueMsg<TelemetryMsg> queueMsg) {
        log.trace("[{}]", queueMsg);

        List<TsKvEntity> tsKvEntities = new ArrayList<>();

        TelemetryMsg telemetryMsg = queueMsg.getData();
        for (Kv kv : telemetryMsg.getKvs()) {
            TsKvEntity tsKvEntity = new TsKvEntity(kv);
            tsKvEntity.setEntityId(telemetryMsg.getEntityId());
            tsKvEntities.add(tsKvEntity);
        }

        tsKvDao.save(tsKvEntities);

    }

    @Override
    public List<TsKvDto> findTsKvByEntityId(UUID entityId, BaseReadQuery query) {
        log.trace("[{}]", entityId);

        List<TsKvEntity> tsKvByEntityId = tsKvDao.findTsKvByEntityId(entityId, query);

        return tsKvByEntityId.stream()
                .map(tsKvEntity -> new TsKvDto(tsKvEntity))
                .collect(Collectors.toList());
    }
}
