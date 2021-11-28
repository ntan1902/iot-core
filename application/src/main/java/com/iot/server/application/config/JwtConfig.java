package com.iot.server.application.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConfigurationProperties(prefix = "security.jwt")
@Log4j2
public class JwtConfig {
    private long accessTokenExp;
    private long refreshTokenExp;
    private String issuer;
    private String signingKey;

    @PostConstruct
    void print() {
        log.info("JwtConfig [{}]", this);
    }
}
