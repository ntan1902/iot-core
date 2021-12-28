package com.iot.server.rest.client;

import com.iot.server.common.request.TenantRequest;
import com.iot.server.common.request.ValidateDeviceTokenRequest;

public interface EntityServiceClient {
    void registerTenant(TenantRequest tenantRequest);

    boolean validateDeviceToken(ValidateDeviceTokenRequest validateDeviceTokenRequest);

}
