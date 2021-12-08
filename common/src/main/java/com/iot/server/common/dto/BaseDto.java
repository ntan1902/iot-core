package com.iot.server.common.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class BaseDto {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createUid;
    private UUID updateUid;
    private boolean deleted;
}

