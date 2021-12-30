package com.iot.server.transport.dto;

import com.iot.server.common.model.TsKvList;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PostTelemetryMsg implements Serializable {
    private TsKvList tsKvList;
}
