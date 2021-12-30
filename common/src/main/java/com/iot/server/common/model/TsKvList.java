package com.iot.server.common.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class TsKvList implements Serializable {
    private long ts;
    private UUID entityId;
    private List<Kv> kv;
}
