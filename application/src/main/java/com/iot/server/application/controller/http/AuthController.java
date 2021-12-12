package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.GetJwkSetHandler;
import com.iot.server.application.controller.handler.RegisterHandler;
import com.iot.server.common.request.EmptyRequest;
import com.iot.server.common.request.RegisterRequest;
import com.iot.server.common.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterHandler registerHandler;
    private final GetJwkSetHandler getJwkSetHandler;

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(registerHandler.handleRequest(registerRequest));
    }

    @GetMapping("/auth/.well-known/jwks.json")
    public ResponseEntity<Map<String, Object>> getJWKSet() {
        return ResponseEntity.ok(getJwkSetHandler.handleRequest(new EmptyRequest()));
    }

    @GetMapping("/auth/hello")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }
}
