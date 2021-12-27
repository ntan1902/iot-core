package com.iot.server.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EntityServiceTimed {

    private final WebClient webClient;

    @Autowired
    public EntityServiceTimed(WebClient webClient) {
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