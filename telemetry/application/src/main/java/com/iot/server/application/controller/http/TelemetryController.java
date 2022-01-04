package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.GetDeviceTelemetryHandler;
import com.iot.server.application.controller.request.GetDeviceTelemetryRequest;
import com.iot.server.application.controller.response.GetDeviceTelemetryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TelemetryController {

    private final GetDeviceTelemetryHandler getDeviceTelemetryHandler;

    @GetMapping("/device/{deviceId}/telemetry")
    public ResponseEntity<GetDeviceTelemetryResponse>
    getDeviceTelemetry(@PathVariable("deviceId") String deviceId,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size,
                       @RequestParam(value = "order", defaultValue = "DESC") String order,
                       @RequestParam(value = "property", defaultValue = "ts") String property) {
        return ResponseEntity.ok(
                getDeviceTelemetryHandler.handleRequest(
                        GetDeviceTelemetryRequest.builder()
                                .deviceId(deviceId)
                                .page(page)
                                .size(size)
                                .order(order)
                                .property(property)
                                .build())
        );
    }
}
