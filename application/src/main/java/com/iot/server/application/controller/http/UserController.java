package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.CreateUserHandler;
import com.iot.server.application.controller.handler.DeleteUserHandler;
import com.iot.server.application.controller.handler.GetUserByIdHandler;
import com.iot.server.application.controller.request.CreateUserRequest;
import com.iot.server.application.controller.request.DeleteUserRequest;
import com.iot.server.application.controller.request.GetUserByIdRequest;
import com.iot.server.application.controller.response.CreateUserResponse;
import com.iot.server.application.controller.response.DeleteUserResponse;
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
    private final DeleteUserHandler deleteUserHandler;

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT', 'CUSTOMER')")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable("id") String id) {
        GetUserByIdRequest request = new GetUserByIdRequest();
        request.setUserId(id);
        return ResponseEntity.ok(getUserByIdHandler.handleRequest(request));
    }

    @PostMapping("/user")
    @PreAuthorize("hasAnyAuthority('TENANT', 'CUSTOMER')")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(createUserHandler.handleRequest(request));
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT', 'CUSTOMER')")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable("id") String id) {
        DeleteUserRequest request = new DeleteUserRequest();
        request.setUserId(id);
        return ResponseEntity.ok(deleteUserHandler.handleRequest(request));
    }
}
