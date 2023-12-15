package com.amen.isa.user.service;

import com.amen.isa.component.repository.UserRepository;
import com.amen.isa.model.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// TODO: Feel me :)
@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private UserRepository storeUserRepository;

    @InjectMocks
    private BasketServiceImpl basketService;

    @Test
    void testGetBasket() {
        String userId = "user123";
        Basket mockBasket = new Basket(new ArrayList<>());
        StoreUser mockStoreUser = StoreUser.builder().userId(userId).basket(mockBasket).build();
        when(storeUserRepository.save(any(StoreUser.class))).thenReturn(Mono.just(mockStoreUser));

        Mono<Basket> result = basketService.getBasket(userId);

        assertNotNull(result);
        result.subscribe(basket -> {
            assertEquals(mockBasket, basket);
        });
    }

    @Test
    void testAddToBasket() {
        String userId = "user123";
        BasketPosition basketPosition = new BasketPosition(1, new Product(), new ProductQuantity(10.0, 1, MeasureUnit.UNIT));
        Basket mockBasket = new Basket(new ArrayList<>());
        StoreUser mockStoreUser = StoreUser.builder().userId(userId).basket(mockBasket).build();
        when(storeUserRepository.findById(userId)).thenReturn(Mono.just(mockStoreUser));
        when(storeUserRepository.save(any(StoreUser.class))).thenReturn(Mono.just(mockStoreUser));

        Mono<Basket> result = basketService.addToBasket(userId, basketPosition);

        result.subscribe(basket -> {
            assertNotNull(basket);
            assertEquals(1, basket.getPositions().size());
        });

    }

    @Test
    void testRemoveFromBasket() {
        String userId = "user123";
        int position = 0; // Assuming index starts from 0
        BasketPosition basketPosition = new BasketPosition(position, new Product(), new ProductQuantity(10.0, 1, MeasureUnit.UNIT));
        Basket mockBasket = new Basket(Collections.singletonList(basketPosition));
        StoreUser mockStoreUser = StoreUser.builder().userId(userId).basket(mockBasket).build();
        when(storeUserRepository.findById(userId)).thenReturn(Mono.just(mockStoreUser));
        when(storeUserRepository.save(any(StoreUser.class))).thenReturn(Mono.just(mockStoreUser));

        Mono<Basket> result = basketService.removeFromBasket(userId, position);

        assertNotNull(result);

        result.subscribe(basket -> {
            assertNotNull(basket);
            assertTrue(basket.getPositions().isEmpty());
        });
    }

}