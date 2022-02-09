package com.iot.server.domain;

import com.iot.server.common.model.TelemetryMsg;

import java.util.Set;
import java.util.UUID;

public interface WebSocketService {
    void sendTelemetry(Set<UUID> userIds, TelemetryMsg telemetryMsg);

    void sendDebugMsg(Set<UUID> userIds, String msg);
}
