package com.iot.server.queue.rabbitmq;

import com.iot.server.queue.QueueProducerTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("rabbitTemplate")
@RequiredArgsConstructor
public class RabbitMQProducerTemplate<T> implements QueueProducerTemplate<T> {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(T msg) {
        try {
            rabbitTemplate.convertAndSend(msg);
        } catch (AmqpException ex) {
            log.error("Failed to publish message [{}]", msg);
        }
    }
}
