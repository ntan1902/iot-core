package com.iot.server.common.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateDeviceTokenRequest {
    private String token;
}
