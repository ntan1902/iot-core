package com.iot.server.common.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueMsg<T> {

    private UUID key;
    private T data;
    private String type;
    private Set<UUID> userIds;
}
