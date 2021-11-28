package com.iot.server.application.controller;

import com.iot.server.application.security.model.RegisterRequest;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.enums.AuthorityEnum;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        UserDto userDto = UserDto.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .authority(AuthorityEnum.getAuthority(registerRequest.getAuthority()))
                .build();

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        UserDto savedUser = userService.saveUser(userDto, encodedPassword);

        return ResponseEntity.ok(savedUser);
    }
}
