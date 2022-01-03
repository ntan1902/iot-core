package com.iot.server.domain;

import com.iot.server.common.model.TelemetryMsg;

public interface WebSocketService {
    void send(TelemetryMsg telemetryMsg);
}
