package com.iot.server.dao.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    public String get(String path, MultiValueMap<String, String> queryParams) {
        Mono<String> responseMono =
                webClient
                        .get()
                        .uri(uriBuilder -> uriBuilder.path(path).queryParams(queryParams).build())
                        .retrieve()
                        .bodyToMono(String.class);

        return responseMono.block();
    }

    public String post(String path, Object body) {
        Mono<String> responseMono =
                webClient
                        .post()
                        .uri(uriBuilder -> uriBuilder.path(path).build())
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(String.class);

        return responseMono.block();
    }
}