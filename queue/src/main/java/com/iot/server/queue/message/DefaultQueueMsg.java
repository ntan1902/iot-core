package com.iot.server.queue.message;

import java.util.UUID;
import lombok.Data;

@Data
public class DefaultQueueMsg<T> {

   private final UUID key;
   private final T data;
}
