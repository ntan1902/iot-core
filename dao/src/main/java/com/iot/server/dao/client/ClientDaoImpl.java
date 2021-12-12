package com.iot.server.dao.client;

import com.iot.server.common.dao.client.ClientDao;
import com.iot.server.common.dao.client.entity.TenantEntity;
import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientDaoImpl implements ClientDao {

    private final ClientDaoTimed clientDaoTimed;
    private final ClientConfig clientConfig;

    @Override
    public void createTenant(TenantEntity tenantEntity) {
        String path = clientConfig.getHost() + ":" + clientConfig.getPort() + "/tenant";
        log.info("Request: {} - Body: {}", path, tenantEntity);
        String responseStr = createTenant(path, tenantEntity, 1);
        log.info("Response: {}", responseStr);
    }

    private String createTenant(String path, TenantEntity tenantEntity, int attempt) {
        try {
            return clientDaoTimed.post(path, tenantEntity);
        } catch (RuntimeException ex) {
            log.warn("Attempt: {} - Reason:", attempt, ex);
            if (attempt >= clientConfig.getMaxAttempt()) {
                throw new IoTException(ReasonEnum.INVALID_PARAMS, "Failed to call client after " + attempt + " attempts");
            }

            if (ex instanceof ReadTimeoutException
                    || ex instanceof WriteTimeoutException
                    || ex.getCause() instanceof ConnectTimeoutException) {

                return createTenant(path, tenantEntity, attempt + 1);
            }

            throw new IoTException(ReasonEnum.INVALID_PARAMS, ex.getMessage());
        }

    }
}
