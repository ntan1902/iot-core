package com.iot.server.queue.topic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicInfo {
    private String topic;
}
