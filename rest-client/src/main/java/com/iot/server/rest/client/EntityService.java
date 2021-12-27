package com.iot.server.rest.client;

import com.iot.server.rest.client.entity.TenantEntity;

public interface EntityService {
    void registerTenant(TenantEntity tenantEntity);
}
