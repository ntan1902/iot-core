package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.CreateUserHandler;
import com.iot.server.application.controller.handler.GetUserByIdHandler;
import com.iot.server.application.controller.request.CreateUserRequest;
import com.iot.server.application.controller.request.GetUserByIdRequest;
import com.iot.server.application.controller.response.CreateUserResponse;
import com.iot.server.application.controller.response.GetUserByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final GetUserByIdHandler getUserByIdHandler;
    private final CreateUserHandler createUserHandler;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT', 'CUSTOMER')")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable("userId") String userId) {
        GetUserByIdRequest request = new GetUserByIdRequest();
        request.setUserId(userId);
        return ResponseEntity.ok(getUserByIdHandler.handleRequest(request));
    }

    @PostMapping("/user")
    @PreAuthorize("hasAnyAuthority('TENANT', 'CUSTOMER')")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(createUserHandler.handleRequest(request));
    }
}
