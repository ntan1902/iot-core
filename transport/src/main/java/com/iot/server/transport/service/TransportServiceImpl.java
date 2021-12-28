package com.iot.server.transport.service;

import com.iot.server.common.request.ValidateDeviceTokenRequest;
import com.iot.server.rest.client.EntityServiceClient;
import com.iot.server.transport.dto.ValidateDeviceToken;
import com.iot.server.transport.enums.TransportType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final EntityServiceClient entityServiceClient;

    @Override
    public boolean process(TransportType transportType, ValidateDeviceToken validateDeviceToken) {
        return entityServiceClient.validateDeviceToken(ValidateDeviceTokenRequest.builder()
                .token(validateDeviceToken.getToken())
                .build());
    }
}
