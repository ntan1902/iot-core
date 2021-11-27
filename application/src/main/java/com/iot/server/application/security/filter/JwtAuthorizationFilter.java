package com.iot.server.application.security.filter;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final List<String> pathsToSkip;

    public JwtAuthorizationFilter(List<String> pathsToSkip) {
        this.pathsToSkip = pathsToSkip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return pathsToSkip
                .stream()
                .anyMatch(path -> new AntPathMatcher().match(path, request.getServletPath()));
    }


}
