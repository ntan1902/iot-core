package com.iot.server.transport.service;

import com.iot.server.rest.client.EntityServiceClient;
import com.iot.server.transport.dto.ValidateDeviceTokenRequest;
import com.iot.server.transport.enums.TransportType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final EntityServiceClient entityServiceClient;

    @Override
    public CompletableFuture<Boolean> process(TransportType transportType, ValidateDeviceTokenRequest validateDeviceTokenRequest) {
        return CompletableFuture.supplyAsync(() -> {
                    throw new IllegalArgumentException();
                },
                Executors.newSingleThreadExecutor());
    }
}
