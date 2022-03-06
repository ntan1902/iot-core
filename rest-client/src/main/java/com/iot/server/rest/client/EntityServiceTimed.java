package com.iot.server.rest.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class EntityServiceTimed {

    private final List<WebClient> webClients = new ArrayList<>();
    private final AtomicInteger currentClientIndex = new AtomicInteger(0);

    @PostConstruct
    void init() {
        for (int i = 0; i < 10; i++) {
            webClients.add(WebClient.builder()
                    .clientConnector(getClientConnector())
                    .build());
        }
    }

    private ClientHttpConnector getClientConnector() {
        // This is implicit default if you use TcpClient#create()
        ConnectionProvider connectionProvider = ConnectionProvider.create("tcp", 500);
        LoopResources loopResources = LoopResources.create("reactor-tcp");

        TcpClient tcpClient =
                TcpClient.create(connectionProvider)
                        .runOn(loopResources)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30_000)
                        .doOnConnected(
                                connection ->
                                        connection
                                                .addHandlerLast(new ReadTimeoutHandler(700,
                                                        TimeUnit.MILLISECONDS))
                                                .addHandlerLast(new WriteTimeoutHandler(700,
                                                        TimeUnit.MILLISECONDS)));

        HttpClient httpClient = HttpClient.from(tcpClient);

        return new ReactorClientHttpConnector(httpClient);

    }

    public String get(String path, MultiValueMap<String, String> queryParams) {
        int currentIndex = getCurrentIndex();

        Mono<String> responseMono =
                webClients.get(currentIndex)
                        .get()
                        .uri(uriBuilder -> uriBuilder.path(path)
                                .queryParams(queryParams)
                                .build())
                        .retrieve()
                        .bodyToMono(String.class);

        return responseMono.block();
    }

    public String post(String path, Object body) {
        int currentIndex = getCurrentIndex();

        Mono<String> responseMono =
                webClients.get(currentIndex)
                        .post()
                        .uri(uriBuilder -> uriBuilder.path(path).build())
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(String.class);

        return responseMono.block();
    }

    private int getCurrentIndex() {
        return currentClientIndex.getAndUpdate(index -> index < (webClients.size() - 1) ? index + 1 : 0);
    }
}