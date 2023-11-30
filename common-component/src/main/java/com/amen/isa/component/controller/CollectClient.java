package com.amen.isa.component.controller;

import com.amen.isa.model.response.NonPrimitiveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class CollectClient {
    private final WebClient userServiceWebClient;

    public Flux<NonPrimitiveResponse> fetch() {
        return userServiceWebClient.get()
                .uri("/provide")
                .retrieve()
                .bodyToFlux(NonPrimitiveResponse.class);
    }
}