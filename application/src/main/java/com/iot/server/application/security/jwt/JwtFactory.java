package com.iot.server.application.security.jwt;

import com.iot.server.application.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtFactory {
    private final JwtConfig jwtConfig;


}
