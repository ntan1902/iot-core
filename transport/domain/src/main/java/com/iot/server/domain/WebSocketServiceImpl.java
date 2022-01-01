package com.iot.server.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void send(String userId, String msg) {
        messagingTemplate.convertAndSend("/topic/telemetry/" + userId, msg);
    }
}
