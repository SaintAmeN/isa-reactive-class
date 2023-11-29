package com.amen.isa.component.client;

import com.amen.isa.model.domain.StoreUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceClient {
    private final WebClient userServiceWebClient;

    public List<StoreUser> getAllBlocking() {
        return userServiceWebClient.get()
                .uri("/user")
                .retrieve()
                .bodyToFlux(StoreUser.class)
                .collectList()
                .block();
    }
}
