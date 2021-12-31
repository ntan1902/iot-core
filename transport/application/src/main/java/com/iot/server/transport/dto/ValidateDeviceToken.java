package com.iot.server.transport.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateDeviceToken {
    private String token;
}
