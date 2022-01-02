package com.iot.server.common.model;

import com.iot.server.common.enums.KvType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Kv {
    private String key;
    private KvType type;
    private Boolean boolV;
    private Long longV;
    private Double doubleV;
    private String stringV;
    private String jsonV;
}
