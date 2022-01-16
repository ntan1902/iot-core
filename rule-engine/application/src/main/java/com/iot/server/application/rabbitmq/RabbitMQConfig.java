package com.iot.server.application.rabbitmq;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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

    private Config ruleEngine;
    private Config deviceClient;

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
    public Queue ruleEngineQueue() {
        return new Queue(ruleEngine.queueName, false);
    }

    @Bean
    public FanoutExchange telemetryExchange() {
        return new FanoutExchange(ruleEngine.exchangeName);
    }

    @Bean
    public Binding telemetryBinding(Queue ruleEngineQueue, FanoutExchange telemetryExchange) {
        return BindingBuilder.bind(ruleEngineQueue).to(telemetryExchange);
    }

    @Bean
    public Queue deviceQueue() {
        return new Queue(deviceClient.queueName, false);
    }

    @Bean
    public FanoutExchange deviceExchange() {
        return new FanoutExchange(deviceClient.exchangeName);
    }

    @Bean
    public Binding deviceBinding(Queue deviceQueue, FanoutExchange deviceExchange) {
        return BindingBuilder.bind(deviceQueue).to(deviceExchange);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(createConnectionFactory(ruleEngine));

        rabbitAdmin.declareExchange(telemetryExchange());
        rabbitAdmin.declareQueue(ruleEngineQueue());
        rabbitAdmin.declareBinding(telemetryBinding(ruleEngineQueue(), telemetryExchange()));

        rabbitAdmin.declareExchange(deviceExchange());
        rabbitAdmin.declareQueue(deviceQueue());
        rabbitAdmin.declareBinding(deviceBinding(deviceQueue(), deviceExchange()));

        return rabbitAdmin;
    }

    @Getter
    @Setter
    public static class Config {
        private String queueName;
        private String exchangeName;
        private String host;
        private int port;
        private String username;
        private String password;
        private int connectionTimeout;
    }

}
