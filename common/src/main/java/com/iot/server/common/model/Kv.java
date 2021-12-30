package com.iot.server.common.model;

import com.iot.server.common.enums.KvType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Kv implements Serializable {
    private String key;
    private KvType type;
    private Boolean boolV;
    private Long longV;
    private Double doubleV;
    private String stringV;
    private String jsonV;
}
