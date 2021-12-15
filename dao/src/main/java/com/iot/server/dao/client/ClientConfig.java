package com.iot.server.dao.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@ConfigurationProperties(prefix = "client.nodejs")
@Component
@Slf4j
public class ClientConfig {
    private String host;
    private int maxAttempt;

    @PostConstruct
    void printClientConfig() {
        log.info("ClientConfig [{}]", this);
    }
}
