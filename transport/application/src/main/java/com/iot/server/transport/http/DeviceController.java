package com.iot.server.transport.http;

import com.iot.server.common.dto.ValidateDeviceToken;
import com.iot.server.common.enums.TransportType;
import com.iot.server.common.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ForkJoinPool;

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

    @GetMapping("/async-deferredresult")
    public DeferredResult<ResponseEntity<?>> handleReqDefResult(Model model) {
        log.info("Received async-deferredresult request");
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();

        ForkJoinPool.commonPool().submit(() -> {
            log.info("Processing in separate thread");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException ignored) {
            }
            output.setResult(ResponseEntity.ok("ok"));
        });

        log.info("servlet thread freed");
        return output;
    }
}
