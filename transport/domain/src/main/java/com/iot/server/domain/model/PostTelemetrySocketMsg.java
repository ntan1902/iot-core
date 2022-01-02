package com.iot.server.domain.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PostTelemetrySocketMsg {
    private UUID deviceId;
    private List<KvSocketMsg> kvs;
}
