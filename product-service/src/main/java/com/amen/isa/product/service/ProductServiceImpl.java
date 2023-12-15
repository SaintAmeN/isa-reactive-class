package com.amen.isa.product.service;

import com.amen.isa.model.contract.IProductService;
import com.amen.isa.model.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Write me :)
@Service
public class ProductServiceImpl implements IProductService {
    @Override
    public Product getProductById(Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }
}
