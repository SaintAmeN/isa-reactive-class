package com.amen.isa.product.service;

import com.amen.isa.component.repository.ProductRepository;
import com.amen.isa.model.domain.MeasureUnit;
import com.amen.isa.model.domain.Product;
import com.amen.isa.model.exception.InvalidRequest;
import com.amen.isa.model.exception.NotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// TODO: Feel me :)
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product mockProduct = new Product(productId, "Test Product", 20.0, MeasureUnit.KILOGRAM);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Mono<Product> result = productService.getProductById(productId);


//        assertEquals(mockProduct, result);
    }

    @Test
    void testGetAllProducts() {
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Product 1", 10.0, MeasureUnit.UNIT),
                new Product(2L, "Product 2", 5.0, MeasureUnit.GRAM)
        );
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(mockProducts.size(), result.size());
    }

    @Test
    void testSaveProduct() {
        Product newProduct = new Product(null, "New Product", 15.0, MeasureUnit.KILOGRAM);
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        Product result = productService.saveProduct(newProduct);

        assertNotNull(result);
        assertEquals(newProduct, result);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testGetProductByIdNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> productService.getProductById(productId));
    }

    @Test
    void testSaveProductWithNullProduct() {
        assertThrows(InvalidRequest.class, () -> productService.saveProduct(null));
    }

    @Test
    void testSaveProductWithExistingId() {
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Existing Product", 15.0, MeasureUnit.KILOGRAM);

        assertThrows(InvalidRequest.class, () -> productService.saveProduct(existingProduct));
    }

    @Test
    void testDeleteProductNotFound() {
        Long productId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(productId);

        assertThrows(InvalidRequest.class, () -> productService.deleteProduct(productId));
    }
}