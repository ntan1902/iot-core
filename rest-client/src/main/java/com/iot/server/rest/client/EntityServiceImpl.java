package com.iot.server.rest.client;

import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import com.iot.server.rest.client.entity.TenantEntity;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityServiceImpl implements EntityService {

    private final EntityServiceTimed entityServiceTimed;
    private final EntityServiceConfig entityServiceConfig;

    @Override
    public void registerTenant(TenantEntity tenantEntity) {
        String path = entityServiceConfig.getHost() + "/auth/register-tenant";
        log.info("Request: {} - Body: {}", path, tenantEntity);
        String responseStr = registerTenant(path, tenantEntity, 1);
        log.info("Response: {}", responseStr);
    }

    private String registerTenant(String path, TenantEntity tenantEntity, int attempt) {
        try {
            return entityServiceTimed.post(path, tenantEntity);
        } catch (RuntimeException ex) {
            log.warn("Attempt: {} - Reason:", attempt, ex);
            if (attempt >= entityServiceConfig.getMaxAttempt()) {
                throw new IoTException(ReasonEnum.INVALID_PARAMS, "Failed to call client after " + attempt + " attempts");
            }

            if (ex instanceof ReadTimeoutException
                    || ex instanceof WriteTimeoutException
                    || ex.getCause() instanceof ConnectTimeoutException) {

                return registerTenant(path, tenantEntity, attempt + 1);
            }

            throw new IoTException(ReasonEnum.INVALID_PARAMS, ex.getMessage());
        }

    }
}
