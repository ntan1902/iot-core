package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.GetUserByIdHandler;
import com.iot.server.application.controller.request.GetUserByIdRequest;
import com.iot.server.application.controller.response.GetUserByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final GetUserByIdHandler getUserByIdHandler;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT', 'CUSTOMER')")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable("userId") String userId) {
        GetUserByIdRequest request = new GetUserByIdRequest();
        request.setUserId(userId);
        return ResponseEntity.ok(getUserByIdHandler.handleRequest(request));
    }
}
