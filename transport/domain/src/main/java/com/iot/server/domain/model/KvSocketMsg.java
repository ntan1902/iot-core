package com.iot.server.domain.model;

import com.iot.server.common.enums.KvType;
import lombok.Data;

@Data
public class KvSocketMsg {
    private Long ts;
    private KvType type;
    private String key;
    private Object value;
}
