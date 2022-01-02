package com.iot.server.domain;

import com.iot.server.common.model.PostTelemetryMsg;

public interface WebSocketService {
    void send(PostTelemetryMsg postTelemetryMsg);
}
