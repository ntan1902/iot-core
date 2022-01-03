package com.iot.server.application;

import com.iot.server.application.controller.mqtt.MqttVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication(scanBasePackages = {"com.iot.server"})
@SpringBootConfiguration
@ComponentScan({"com.iot.server"})
public class Application {

    @Autowired
    private MqttVerticle mqttVerticle;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void initMqttServer() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(mqttVerticle);
    }
}
