package com.iot.server.transport.service;

import com.iot.server.common.enums.DeviceCredentialsType;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.request.ValidateDeviceRequest;
import com.iot.server.common.response.DeviceResponse;
import com.iot.server.rest.client.EntityServiceClient;
import com.iot.server.transport.dto.ValidateDeviceToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final EntityServiceClient entityServiceClient;

    @Override
    public void process(TransportType transportType, ValidateDeviceToken validateDeviceToken) {
        DeviceResponse deviceResponse = entityServiceClient.validateDevice(ValidateDeviceRequest.builder()
                .token(validateDeviceToken.getToken())
                .type(DeviceCredentialsType.ACCESS_TOKEN)
                .build());
        if (deviceResponse == null) {
            throw new IllegalArgumentException("Device token is not valid");
        }
    }
}
