package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import com.amen.isa.model.domain.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long productId);

    List<Product> getAllProducts();

    Product saveProduct(Product product);

    void deleteProduct(Long productId);
}

