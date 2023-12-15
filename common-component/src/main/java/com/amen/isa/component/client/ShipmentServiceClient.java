package com.amen.isa.component.client;

import com.amen.isa.model.domain.Product;
import com.amen.isa.model.domain.Shipment;
import com.amen.isa.model.response.OrderResponse;
import com.amen.isa.model.response.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShipmentServiceClient {
    private final WebClient shipmentServiceWebClient;

    public Mono<Void> submitShipments(List<Product> products) {
        return shipmentServiceWebClient.post()
                .uri("/shipment")
                .bodyValue(products)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // TODO: Write me :)
    public Flux<ShipmentResponse> fetchShipmentsBetweenDates(LocalDateTime any, LocalDateTime end) {
        return null;
    }
}
