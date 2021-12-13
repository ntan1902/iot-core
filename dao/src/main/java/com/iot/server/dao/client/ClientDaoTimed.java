package com.iot.server.dao.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClientDaoTimed {

    private final WebClient webClient;

    @Autowired
    public ClientDaoTimed(WebClient webClient) {
        this.webClient = webClient;
    }

    String get(String path, MultiValueMap<String, String> queryParams) {
        Mono<String> responseMono =
                webClient
                        .get()
                        .uri(uriBuilder -> uriBuilder.path(path).queryParams(queryParams).build())
                        .retrieve()
                        .bodyToMono(String.class);

        return responseMono.block();
    }


    String post(String path, Object body, String accessToken) {
        Mono<String> responseMono =
                webClient
                        .post()
                        .uri(uriBuilder -> uriBuilder.path(path).build())
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(String.class);

        return responseMono.block();
    }
}