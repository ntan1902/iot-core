package com.iot.server.domain.ts;

import com.iot.server.common.model.PostTelemetryMsg;
import com.iot.server.queue.message.DefaultQueueMsg;

public interface TsKvService {
    void saveOrUpdate(DefaultQueueMsg<PostTelemetryMsg> defaultQueueMsg);
}
