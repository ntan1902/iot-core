package com.iot.server.telemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.iot.server"})
@SpringBootConfiguration
@EnableJpaRepositories(basePackages = {"com.iot.server"})
@EntityScan(basePackages = {"com.iot.server"})
@ComponentScan({"com.iot.server"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
