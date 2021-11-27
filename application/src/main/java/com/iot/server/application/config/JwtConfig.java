package com.iot.server.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
    private long accessTokenExpMs;
    private long refreshTokenExpMs;
    private String issuer;
    private String signingKey;
}
