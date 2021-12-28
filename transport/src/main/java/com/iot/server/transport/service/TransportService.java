package com.iot.server.transport.service;

import com.iot.server.transport.dto.ValidateDeviceTokenRequest;
import com.iot.server.transport.enums.TransportType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

public interface TransportService {
    CompletableFuture<Boolean> process(TransportType transportType, ValidateDeviceTokenRequest validateDeviceTokenRequest);
}
