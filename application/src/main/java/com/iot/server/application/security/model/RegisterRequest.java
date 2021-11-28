package com.iot.server.application.security.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterRequest {
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "First name must be not empty")
    private String firstName;

    @NotEmpty(message = "Last name must be not empty")
    private String lastName;

    @NotEmpty(message = "Authority must be not empty")
    private String authority;

    @NotEmpty(message = "Password must be not empty")
    private String password;
}
