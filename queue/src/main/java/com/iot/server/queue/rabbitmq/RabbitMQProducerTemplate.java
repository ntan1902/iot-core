package com.iot.server.queue.rabbitmq;

import com.iot.server.queue.QueueProducerTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("rabbitProducerTemplate")
@RequiredArgsConstructor
public class RabbitMQProducerTemplate implements QueueProducerTemplate {

    private final AmqpTemplate amqpTemplate;

    @Override
    public void send(String msg) {
        try {
            amqpTemplate.convertAndSend(msg);
        } catch (AmqpException ex) {
            log.error("Failed to publish message [{}]", msg);
        }
    }
}
