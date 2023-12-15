package com.amen.isa.component.client;

import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderServiceClient {
    private final WebClient orderServiceWebClient;

    public Flux<OrderResponse> getOrders() {
        return orderServiceWebClient.get()
                .uri("/order")
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    // TODO: Write me :)
    public Flux<StoreOrder> fetchOrdersBetweenDates(LocalDateTime any, LocalDateTime end) {
        return null;
    }
}
