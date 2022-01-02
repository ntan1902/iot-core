package com.iot.server.rest.client;

import com.iot.server.common.request.TenantRequest;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;

public interface EntityServiceClient {
    String registerTenant(TenantRequest tenantRequest);

    DeviceResponse validateDevice(ValidateDeviceRequest validateDeviceRequest);

}
