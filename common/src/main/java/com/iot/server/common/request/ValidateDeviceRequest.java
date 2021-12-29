package com.iot.server.common.request;

import com.iot.server.common.enums.DeviceCredentialsType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateDeviceRequest {
    private String token;
    private DeviceCredentialsType type;
}
