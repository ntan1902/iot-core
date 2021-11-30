package com.iot.server.application.config;

import com.iot.server.application.utils.RSAUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@Data
@Component
@ConfigurationProperties(prefix = "security.jwt")
@Slf4j
public class JwtConfig {
    private long accessTokenExp;
    private long refreshTokenExp;
    private String issuer;

    private String privateKeyFile;
    private String publicKeyFile;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    void loadRSAKeyPair() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        publicKey = RSAUtils.getPublicKey(publicKeyFile);
        privateKey = RSAUtils.getPrivateKey(privateKeyFile);

        log.info("JwtConfig [{}]", this);
    }
}
