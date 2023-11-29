package com.amen.isa.component.client;

import com.amen.isa.model.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
public class ProductServiceClient {
    private final RestTemplate productServiceRestTemplate;

    public List<Product> getAllBlocking() {
        ResponseEntity<List<Product>> responseEntity = productServiceRestTemplate.exchange("http://localhost:8082/product",
                                                                                           HttpMethod.GET,
                                                                                           HttpEntity.EMPTY,
                                                                                           new ParameterizedTypeReference<>() {
                                                                                           });
        return responseEntity.getBody();
    }
}
