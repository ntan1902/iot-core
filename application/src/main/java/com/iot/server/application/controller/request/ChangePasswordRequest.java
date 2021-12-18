package com.iot.server.application.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordRequest {
    @NotEmpty(message = "Current password must be not empty")
    private String currentPassword;

    @NotEmpty(message = "New password must be not empty")
    private String newPassword;
}
