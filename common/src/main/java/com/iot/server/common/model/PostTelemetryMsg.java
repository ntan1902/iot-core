package com.iot.server.common.model;

import com.iot.server.common.model.TsKvList;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PostTelemetryMsg implements Serializable {
    private TsKvList tsKvList;
}
