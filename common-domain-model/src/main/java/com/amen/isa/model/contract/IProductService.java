package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProductService {
    Mono<Product> getProductById(Long productId);

    Flux<Product> getAllProducts();

    Mono<Product> saveProduct(Product product);

    void deleteProduct(Long productId);
}

