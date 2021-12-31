package com.iot.server.auth.controller.http;

import com.iot.server.auth.controller.handler.CreateUserHandler;
import com.iot.server.auth.controller.handler.DeleteUserHandler;
import com.iot.server.auth.controller.handler.GetUserByIdHandler;
import com.iot.server.auth.controller.handler.UpdateUserHandler;
import com.iot.server.auth.controller.request.CreateUserRequest;
import com.iot.server.auth.controller.request.DeleteUserRequest;
import com.iot.server.auth.controller.request.GetUserByIdRequest;
import com.iot.server.auth.controller.request.UpdateUserRequest;
import com.iot.server.auth.controller.response.CreateUserResponse;
import com.iot.server.auth.controller.response.DeleteUserResponse;
import com.iot.server.auth.controller.response.GetUserByIdResponse;
import com.iot.server.auth.controller.response.UpdateUserResponse;
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
    private final UpdateUserHandler updateUserHandler;

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

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT', 'CUSTOMER')")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable("id") String id,
                                                         @RequestBody @Valid UpdateUserRequest request) {
        if (request.getId() == null || request.getId().isEmpty()) {
            request.setId(id);
        }
        return ResponseEntity.ok(updateUserHandler.handleRequest(request));
    }
}
