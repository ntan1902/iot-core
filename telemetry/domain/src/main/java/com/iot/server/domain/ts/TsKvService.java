package com.iot.server.domain.ts;

import com.iot.server.common.model.BaseReadQuery;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.dao.dto.TsKvDto;
import com.iot.server.common.queue.QueueMsg;

import java.util.List;
import java.util.UUID;

public interface TsKvService {
    void saveOrUpdate(QueueMsg<TelemetryMsg> queueMsg);

    List<TsKvDto> findTsKvByEntityId(UUID entityId, BaseReadQuery query);

    List<TsKvDto> findTsKvLatestByEntityId(UUID entityId);
}
