package com.iot.server.domain;

public interface WebSocketService {
    void send(String userId, String msg);
}
