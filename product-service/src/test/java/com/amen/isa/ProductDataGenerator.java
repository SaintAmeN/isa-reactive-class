package com.amen.isa;

import com.amen.isa.component.repository.ProductRepository;
import com.amen.isa.model.domain.MeasureUnit;
import com.amen.isa.model.domain.Product;
import com.amen.isa.product.ProductServiceApplication;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ProductServiceApplication.class)
@ExtendWith(SpringExtension.class)
class ProductDataGenerator {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void generateProducts() {
        var faker = new Faker();
        for (int i = 0; i < 10; i++) {
            var product = new Product(null, faker.food().ingredient(), 5, MeasureUnit.UNIT);
            productRepository.save(product);
        }
    }
}