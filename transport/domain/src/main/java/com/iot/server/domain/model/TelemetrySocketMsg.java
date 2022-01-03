package com.iot.server.domain.model;

import com.iot.server.common.model.Kv;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TelemetrySocketMsg {
    private UUID entityId;
    private List<Kv> kvs;
}
