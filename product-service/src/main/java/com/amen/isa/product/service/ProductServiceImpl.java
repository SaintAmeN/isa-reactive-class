package com.amen.isa.product.service;

import com.amen.isa.model.contract.IProductService;
import com.amen.isa.model.domain.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: Write me :)
@Service
public class ProductServiceImpl implements IProductService {
    @Override
    public Mono<Product> getProductById(Long productId) {
        return null;
    }

    @Override
    public Flux<Product> getAllProducts() {
        return null;
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }
    // Implement methods
}
