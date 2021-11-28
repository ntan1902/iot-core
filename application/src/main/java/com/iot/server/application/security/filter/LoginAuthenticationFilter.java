package com.iot.server.application.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.server.application.exception.IoTExceptionHandler;
import com.iot.server.application.security.model.LoginRequest;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IoTExceptionHandler ioTExceptionHandler;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            log.error("Authentication method not supported. Request method: " + request.getMethod());
            throw new AuthenticationServiceException("Authentication method not supported");
        }
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            throw new AuthenticationServiceException("Username or Password is invalid");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                email,
                password
        );
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("Login successfully [{}]", request);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ioTExceptionHandler.handle(response, failed);
    }
}
