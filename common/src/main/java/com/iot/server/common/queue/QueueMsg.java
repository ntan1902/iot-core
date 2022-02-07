package com.iot.server.common.queue;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueMsg<T> {

   private UUID key;
   private T data;
   private String type;
   private UUID userId;
}
