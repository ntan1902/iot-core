package com.iot.server.common.model;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TsKvList {

   private long ts;
   private UUID entityId;
   private List<Kv> kv;
}
