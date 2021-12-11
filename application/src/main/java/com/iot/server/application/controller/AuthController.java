package com.iot.server.application.controller;

import com.iot.server.common.dto.UserDto;
import com.iot.server.common.request.RegisterRequest;
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
    private final UserService userService;
    private final JWKSet jwkSet;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @GetMapping("/.well-known/jwks.json")
    public ResponseEntity<Map<String, Object>> getJWKSet() {
        return ResponseEntity.ok(jwkSet.toJSONObject());
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }
}
