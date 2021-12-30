package com.iot.server.queue.message;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class DefaultQueueMsg<T extends Serializable> implements Serializable {
    private final UUID key;
    private final T data;
}
