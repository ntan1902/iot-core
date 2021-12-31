package com.iot.server.auth.controller.response;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserResponse {
    private UUID userId;
}
