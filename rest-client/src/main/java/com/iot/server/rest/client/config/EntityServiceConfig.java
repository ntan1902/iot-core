package com.iot.server.rest.client.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@ConfigurationProperties(prefix = "entity.service")
@Component
@Slf4j
public class EntityServiceConfig {
    private String host;
    private int maxAttempt;

    @PostConstruct
    void printClientConfig() {
        log.info("EntityServiceConfig [{}]", this);
    }
}
