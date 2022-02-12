package com.iot.server.application.rabbitmq;


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

    private Config ruleEngine;
    private Config telemetry;
    private Config debug;
    private Config alarm;
    private Config mqtt;

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
    public FanoutExchange ruleEngineExchange() {
        return new FanoutExchange(ruleEngine.exchangeName);
    }

    @Bean
    public Binding ruleEngineBinding(Queue ruleEngineQueue, FanoutExchange ruleEngineExchange) {
        return BindingBuilder.bind(ruleEngineQueue).to(ruleEngineExchange);
    }

    @Bean
    public Queue mqttQueue() {
        return new Queue(mqtt.queueName);
    }

    @Bean
    public TopicExchange mqttExchange() {
        return new TopicExchange(mqtt.exchangeName);
    }

    @Bean
    public Binding mqttBinding(Queue mqttQueue, TopicExchange mqttExchange) {
        return BindingBuilder.bind(mqttQueue).to(mqttExchange).with(mqtt.routingKey);
    }

    @Bean
    public RabbitTemplate mqttRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(createConnectionFactory(mqtt));
        rabbitTemplate.setExchange(mqtt.exchangeName);
        rabbitTemplate.setRoutingKey(mqtt.routingKey);
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate telemetryRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(createConnectionFactory(telemetry));
        rabbitTemplate.setExchange(telemetry.exchangeName);
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate debugRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(createConnectionFactory(debug));
        rabbitTemplate.setExchange(debug.exchangeName);
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate alarmRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(createConnectionFactory(alarm));
        rabbitTemplate.setExchange(alarm.exchangeName);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(createConnectionFactory(ruleEngine));

        rabbitAdmin.declareExchange(ruleEngineExchange());
        rabbitAdmin.declareQueue(ruleEngineQueue());
        rabbitAdmin.declareBinding(ruleEngineBinding(ruleEngineQueue(), ruleEngineExchange()));

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
        private String routingKey;
    }

}
