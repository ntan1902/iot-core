package com.iot.server.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrCreateDeviceRequest {
    private String name;
    private String label;
    private UUID tenantId;
    private UUID firstTenantId;
}
