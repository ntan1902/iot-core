package com.iot.server.domain.ts;

import com.iot.server.common.model.PostTelemetryMsg;
import com.iot.server.queue.message.QueueMsg;

public interface TsKvService {
    void saveOrUpdate(QueueMsg<PostTelemetryMsg> queueMsg);
}
