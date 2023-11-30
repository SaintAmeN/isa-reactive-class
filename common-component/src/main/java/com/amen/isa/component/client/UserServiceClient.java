package com.amen.isa.component.client;

import com.amen.isa.component.exception.CustomException;
import com.amen.isa.model.domain.StoreUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class UserServiceClient {
    private final WebClient userServiceWebClient;

    // 1  (1s) 2 (2-4s) 3 (4-16)
    public List<StoreUser> getAllBlocking() {
        return userServiceWebClient.get()
                .uri("/user")
                .retrieve()
                .bodyToFlux(StoreUser.class)
                .collectList()
                .block();
    }

    public Flux<StoreUser> getUsersRertryOn5xx() {
        return userServiceWebClient.get()
                .uri("/user")
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CustomException()))
                .bodyToFlux(StoreUser.class)
                .retryWhen(Retry.max(3).filter(t -> t instanceof CustomException));

    }

    public Flux<StoreUser> getUsersRertryOnNon404() {
        return userServiceWebClient.get()
                .uri("/user")
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value() == 404, clientResponse -> Mono.empty())
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CustomException()))
                .bodyToFlux(StoreUser.class)
                .retryWhen(Retry.max(3).filter(t -> t instanceof CustomException));

    }

    // scheduler
    //  -> kod synchroniczny
    //      -> odpytać reaktywnych klientów
    //          -> Flux/Mono
    //
    //  -> call endpoint
    class FailOn404 implements Function<ClientResponse, Mono<? extends Throwable>> {

        @Override
        public Mono<? extends Throwable> apply(ClientResponse clientResponse) {
            return null;
        }
    }
}
