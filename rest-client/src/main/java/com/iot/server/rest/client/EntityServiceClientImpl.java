package com.iot.server.rest.client;

import com.iot.server.common.request.TenantRequest;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;
import com.iot.server.common.response.ValidateDeviceResponse;
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
    public DeviceResponse validateDevice(ValidateDeviceRequest validateDeviceRequest) {
        String path = entityServiceConfig.getHost() + "/device/validate";
        String responseStr = validateDeviceToken(path, validateDeviceRequest, 1);
        log.info("Request: {} - Body: {} - Response: {}", path, validateDeviceRequest, responseStr);

        ValidateDeviceResponse response = GsonUtils.fromJson(responseStr, ValidateDeviceResponse.class);

        return response.getDevice();
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

    private String validateDeviceToken(String path, ValidateDeviceRequest validateDeviceRequest, int attempt) {
        try {
            return entityServiceTimed.post(path, validateDeviceRequest);
        } catch (RuntimeException ex) {
            log.warn("Attempt: {} - Reason:", attempt, ex);
            if (attempt >= entityServiceConfig.getMaxAttempt()) {
                throw new IllegalArgumentException("Failed to call client after " + attempt + " attempts");
            }

            if (ex instanceof ReadTimeoutException
                    || ex instanceof WriteTimeoutException
                    || ex.getCause() instanceof ConnectTimeoutException) {

                return validateDeviceToken(path, validateDeviceRequest, attempt + 1);
            }

            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
