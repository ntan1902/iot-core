package com.iot.server.transport.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateDeviceToken {
    private String token;
}
