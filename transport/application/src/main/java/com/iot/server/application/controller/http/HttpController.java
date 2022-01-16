package com.iot.server.application.controller.http;

import com.iot.server.application.controller.request.PostTelemetryRequest;
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
public class HttpController {
    private final TransportService transportService;

    @PostMapping("/telemetry")
    public CompletableFuture<ResponseEntity<?>> postTelemetry(@RequestBody PostTelemetryRequest request) {
        return CompletableFuture
                .runAsync(() -> transportService.process(
                        TransportType.DEFAULT,
                        ValidateDeviceToken.builder().token(request.getToken().toString()).build(),
                        request.getJson().toString())
                )
                .exceptionally(t -> {
                    throw new CompletionException(t);
                })
                .thenApply(r -> ResponseEntity.ok().build());
    }
}
