package com.iot.server.domain.ts;

import com.iot.server.common.model.Kv;
import com.iot.server.common.model.PostTelemetryMsg;
import com.iot.server.common.model.TsKvList;
import com.iot.server.dao.entity.TsKvEntity;
import com.iot.server.dao.ts.TsKvDao;
import com.iot.server.queue.message.DefaultQueueMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TsKvServiceImpl implements TsKvService {

    private final TsKvDao tsKvDao;

    @Override
    public void saveOrUpdate(DefaultQueueMsg<PostTelemetryMsg> defaultQueueMsg) {
        log.trace("[{}]", defaultQueueMsg);

        List<TsKvEntity> tsKvEntities = new ArrayList<>();

        PostTelemetryMsg postTelemetryMsg = defaultQueueMsg.getData();
        TsKvList tsKvList = postTelemetryMsg.getTsKvList();
        for (Kv kv : tsKvList.getKv()) {
            TsKvEntity tsKvEntity = new TsKvEntity(kv);

            tsKvEntity.setEntityId(tsKvList.getEntityId());
            tsKvEntity.setTs(tsKvList.getTs());

            tsKvEntities.add(tsKvEntity);
        }

        tsKvDao.save(tsKvEntities);

    }
}
