package com.amen.isa.component.client;

import com.amen.isa.model.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductServiceClient {
    private final RestTemplate productServiceRestTemplate;
    private final WebClient productServiceWebClient;

    public Mono<Product> getProductById(Long productId) {
        return Mono.deferContextual(contextView -> {
                    String tracingId = contextView.get("tracing-id");
                    log.info("TracingId: {}", tracingId);

                    var result = productServiceWebClient.get()
                            .uri("/product/byId?productId=" + productId)
                            .header("x-tracing-id", tracingId)
                            .retrieve()
                            .bodyToMono(Product.class)
                            .mapNotNull(product -> product)
                            .contextWrite(Context.of("tracing-id", tracingId));

                    log.info("Result: {}", result);
                    return result;
                })
                .mapNotNull(product -> product)
                .contextWrite(Context.of("tracing-id", "123-tracingId"));
    }

    public List<Product> getAllBlocking() {
        ResponseEntity<List<Product>> responseEntity = productServiceRestTemplate.exchange("http://localhost:8082/product",
                                                                                           HttpMethod.GET,
                                                                                           HttpEntity.EMPTY,
                                                                                           new ParameterizedTypeReference<>() {
                                                                                           });
        return responseEntity.getBody();
    }

    public Flux<Product> getProducts() {
        return Flux.fromIterable(getAllBlocking());
    }

    public Mono<Map<Long, Product>> getProductsMap() {
        return getProducts()
                .collectMap(Product::getId,
                            product -> product);
    }

    // TODO: Write me :)
    public Flux<Product> fetchAvailableProducts() {
        return null;
    }

    public Mono<Product> getProductById(Long productId, String t) {
        log.info("Span: {}", t);
        var product = productServiceWebClient.get()
                .uri("/product/byId?productId=" + productId)
                .header("x-tracing-id", String.valueOf(t))
                .retrieve()
                .bodyToMono(Product.class)
                .block();
        return Mono.just(product);
    }
}
