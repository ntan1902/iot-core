package com.iot.server.transport.config;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "queue.rabbitmq")
@Configuration
@Component
@Slf4j
public class RabbitMQConfig {

    private Config telemetry;

    public ConnectionFactory createConnectionFactory(Config config) {
        CachingConnectionFactory rabbitConnectionFactory = new CachingConnectionFactory();
        rabbitConnectionFactory.setHost(config.host);
        rabbitConnectionFactory.setPort(config.port);
        rabbitConnectionFactory.setUsername(config.username);
        rabbitConnectionFactory.setPassword(config.password);
        rabbitConnectionFactory.setConnectionTimeout(config.connectionTimeout);
        return rabbitConnectionFactory;
    }

    @Bean
    public Queue telemetryQueue() {
        return new Queue(telemetry.queueName, false);
    }

    @Bean
    public DirectExchange telemetryExchange() {
        return new DirectExchange(telemetry.exchangeName);
    }

    @Bean
    public Binding telemetryBinding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(telemetry.routingKey);
    }

    @Bean
    public RabbitTemplate telemetryRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(createConnectionFactory(telemetry));
        rabbitTemplate.setRoutingKey(telemetry.routingKey);
        rabbitTemplate.setExchange(telemetry.exchangeName);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin telemetryRabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(createConnectionFactory(telemetry));

        rabbitAdmin.declareExchange(telemetryExchange());
        rabbitAdmin.declareQueue(telemetryQueue());
        rabbitAdmin.declareBinding(telemetryBinding(telemetryQueue(), telemetryExchange()));

        return rabbitAdmin;
    }

    @Getter
    @Setter
    public static class Config {
        private String queueName;
        private String exchangeName;
        private String routingKey;
        private String host;
        private int port;
        private String username;
        private String password;
        private int connectionTimeout;
    }

}
