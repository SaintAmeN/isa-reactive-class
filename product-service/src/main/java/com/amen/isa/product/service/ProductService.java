package com.amen.isa.product.service;

import com.amen.isa.component.repository.ProductRepository;
import com.amen.isa.model.domain.Product;
import com.amen.isa.model.mapper.ProductMapper;
import com.amen.isa.model.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product addProduct(final AddProductRequest request) {
        var product = productMapper.addProductRequestToProduct(request);
        return productRepository.save(product);
    }
}
