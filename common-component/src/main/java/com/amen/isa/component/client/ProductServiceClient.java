package com.amen.isa.component.client;

import com.amen.isa.model.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductServiceClient {
    private final RestTemplate productServiceRestTemplate;
    private final WebClient productServiceWebClient;

    public Mono<Product> getProductById(Long productId) {
        return productServiceWebClient.get()
                .uri("/product/byId?productId=" + productId)
                .retrieve()
                .bodyToMono(Product.class);
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
}
