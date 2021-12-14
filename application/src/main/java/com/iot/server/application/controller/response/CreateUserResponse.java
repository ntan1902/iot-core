package com.iot.server.application.controller.response;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserResponse {
    private UUID userId;
}
