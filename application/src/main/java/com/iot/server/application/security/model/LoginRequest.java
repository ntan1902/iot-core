package com.iot.server.application.security.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
