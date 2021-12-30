package com.iot.server.queue;

public interface QueueProducerTemplate<T> {

    void send(T msg);
}
