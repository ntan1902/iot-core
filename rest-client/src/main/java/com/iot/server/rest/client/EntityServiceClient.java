package com.iot.server.rest.client;

import com.iot.server.common.request.TenantRequest;

public interface EntityServiceClient {
    void registerTenant(TenantRequest tenantRequest);

    boolean validateDeviceToken(String token);

}
