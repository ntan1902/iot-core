package com.iot.server.application.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDeviceTelemetryRequest {
    private String deviceId;
}
