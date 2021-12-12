package com.iot.server.common.dao.client;

import com.iot.server.common.dao.client.entity.TenantEntity;

public interface ClientDao {
    void createTenant(TenantEntity tenantEntity);
}
