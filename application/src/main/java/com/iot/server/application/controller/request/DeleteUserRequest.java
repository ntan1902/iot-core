package com.iot.server.application.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeleteUserRequest {
    @NotEmpty(message = "User id must not be empty")
    private String userId;
}
