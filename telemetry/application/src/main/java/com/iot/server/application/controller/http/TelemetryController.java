package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.GetDeviceTelemetryHandler;
import com.iot.server.application.controller.request.GetDeviceTelemetryRequest;
import com.iot.server.application.controller.response.GetDeviceTelemetryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TelemetryController {

    private final GetDeviceTelemetryHandler getDeviceTelemetryHandler;

    @GetMapping("/device/{deviceId}/telemetry")
    public ResponseEntity<GetDeviceTelemetryResponse> getDeviceTelemetry(@PathVariable("deviceId") String deviceId) {
        return ResponseEntity.ok(
                getDeviceTelemetryHandler.handleRequest(
                        GetDeviceTelemetryRequest.builder()
                                .deviceId(deviceId)
                                .build())
        );
    }
}
