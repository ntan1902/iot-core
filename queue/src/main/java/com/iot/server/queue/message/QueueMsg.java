package com.iot.server.queue.message;

import java.util.UUID;
import lombok.Data;

@Data
public class QueueMsg<T> {

   private final UUID key;
   private final T data;
}
