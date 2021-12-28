package com.iot.server.rest.client;

import com.iot.server.common.request.TenantRequest;
import com.iot.server.common.request.ValidateDeviceTokenRequest;
import com.iot.server.common.response.ValidateDeviceTokenResponse;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.rest.client.config.EntityServiceConfig;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityServiceClientImpl implements EntityServiceClient {

    private final EntityServiceTimed entityServiceTimed;
    private final EntityServiceConfig entityServiceConfig;

    @Override
    public void registerTenant(TenantRequest tenantRequest) {
        String path = entityServiceConfig.getHost() + "/auth/register-tenant";
        String responseStr = registerTenant(path, tenantRequest, 1);
        log.info("Request: {} - Body: {} - Response: {}", path, tenantRequest, responseStr);
    }

    @Override
    public boolean validateDeviceToken(ValidateDeviceTokenRequest validateDeviceTokenRequest) {
        String path = entityServiceConfig.getHost() + "/device/validate-access-token";
        String responseStr = validateDeviceToken(path, validateDeviceTokenRequest, 1);
        log.info("Request: {} - Body: {} - Response: {}", path, validateDeviceTokenRequest, responseStr);

        ValidateDeviceTokenResponse response = GsonUtils.fromJson(responseStr, ValidateDeviceTokenResponse.class);

        return response.getValid();
    }

    private String registerTenant(String path, TenantRequest tenantRequest, int attempt) {
        try {
            return entityServiceTimed.post(path, tenantRequest);
        } catch (RuntimeException ex) {
            log.warn("Attempt: {} - Reason:", attempt, ex);
            if (attempt >= entityServiceConfig.getMaxAttempt()) {
                throw new IllegalArgumentException("Failed to call client after " + attempt + " attempts");
            }

            if (ex instanceof ReadTimeoutException
                    || ex instanceof WriteTimeoutException
                    || ex.getCause() instanceof ConnectTimeoutException) {

                return registerTenant(path, tenantRequest, attempt + 1);
            }

            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    private String validateDeviceToken(String path, ValidateDeviceTokenRequest validateDeviceTokenRequest, int attempt) {
        try {
            return entityServiceTimed.post(path, validateDeviceTokenRequest);
        } catch (RuntimeException ex) {
            log.warn("Attempt: {} - Reason:", attempt, ex);
            if (attempt >= entityServiceConfig.getMaxAttempt()) {
                throw new IllegalArgumentException("Failed to call client after " + attempt + " attempts");
            }

            if (ex instanceof ReadTimeoutException
                    || ex instanceof WriteTimeoutException
                    || ex.getCause() instanceof ConnectTimeoutException) {

                return validateDeviceToken(path, validateDeviceTokenRequest, attempt + 1);
            }

            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
