package com.iot.server.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class TelemetryMsg {
   private UUID entityId;
   private UUID userId;
   private List<Kv> kvs;
}
