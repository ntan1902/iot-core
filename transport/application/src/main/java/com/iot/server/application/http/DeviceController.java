package com.iot.server.application.http;

import com.iot.server.common.enums.TransportType;
import com.iot.server.domain.TransportService;
import com.iot.server.domain.model.ValidateDeviceToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {
    private final TransportService transportService;

    @PostMapping("/{deviceToken}/telemetry")
    public CompletableFuture<ResponseEntity<?>> sendTelemetry(@PathVariable("deviceToken") String deviceToken,
                                                              @RequestBody String json) {
        return CompletableFuture
                .runAsync(() -> transportService.process(
                        TransportType.DEFAULT,
                        ValidateDeviceToken.builder().token(deviceToken).build(),
                        json))
                .exceptionally(t -> {
                    throw new CompletionException(t);
                })
                .thenApply(r -> ResponseEntity.ok().build());
    }
}
