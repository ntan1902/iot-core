package com.iot.server.domain;

import com.iot.server.common.model.TelemetryMsg;

public interface WebSocketService {
    void sendTelemetry(String userId, TelemetryMsg telemetryMsg);

    void sendDebugMsg(String userId, String msg);
}
