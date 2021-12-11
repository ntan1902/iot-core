package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.GetJwkSetHandler;
import com.iot.server.application.controller.handler.RegisterHandler;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.request.EmptyRequest;
import com.iot.server.common.request.RegisterRequest;
import com.iot.server.common.response.RegisterResponse;
import com.iot.server.common.service.UserService;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterHandler registerHandler;
    private final GetJwkSetHandler getJwkSetHandler;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(registerHandler.handleRequest(registerRequest));
    }

    @GetMapping("/.well-known/jwks.json")
    public ResponseEntity<Map<String, Object>> getJWKSet() {
        return ResponseEntity.ok(getJwkSetHandler.handleRequest(new EmptyRequest()));
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }
}
