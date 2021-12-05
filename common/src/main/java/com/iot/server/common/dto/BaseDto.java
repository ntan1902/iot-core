package com.iot.server.common.dto;


import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
public abstract class BaseDto {

  private UUID id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}

