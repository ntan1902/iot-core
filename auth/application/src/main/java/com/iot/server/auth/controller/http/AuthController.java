package com.iot.server.auth.controller.http;

import com.iot.server.auth.controller.handler.ChangePasswordHandler;
import com.iot.server.auth.controller.handler.GetJwkSetHandler;
import com.iot.server.auth.controller.handler.RegisterHandler;
import com.iot.server.auth.controller.request.ChangePasswordRequest;
import com.iot.server.auth.controller.request.EmptyRequest;
import com.iot.server.auth.controller.request.RegisterRequest;
import com.iot.server.auth.controller.response.ChangePasswordResponse;
import com.iot.server.auth.controller.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterHandler registerHandler;
    private final GetJwkSetHandler getJwkSetHandler;
    private final ChangePasswordHandler changePasswordHandler;

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(registerHandler.handleRequest(request));
    }

    @PostMapping("/auth/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request ) {
        return ResponseEntity.ok(changePasswordHandler.handleRequest(request));
    }

    @GetMapping("/auth/.well-known/jwks.json")
    public ResponseEntity<Map<String, Object>> getJWKSet() {
        return ResponseEntity.ok(getJwkSetHandler.handleRequest(new EmptyRequest()));
    }
}
