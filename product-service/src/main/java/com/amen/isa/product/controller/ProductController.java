package com.amen.isa.product.controller;

import com.amen.isa.model.domain.Product;
import com.amen.isa.model.request.AddProductRequest;
import com.amen.isa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PostMapping()
    public Product add(@RequestBody AddProductRequest request) {
        return productService.addProduct(request);
    }
}

