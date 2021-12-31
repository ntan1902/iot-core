package com.iot.server.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostTelemetryMsg {

   private TsKvList tsKvList;
}
