package com.iot.server.application.controller.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UpdateUserRequest {
    private String id;

    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "First name must be not empty")
    private String firstName;

    @NotEmpty(message = "Last name must be not empty")
    private String lastName;

    private Boolean deleted;
}
