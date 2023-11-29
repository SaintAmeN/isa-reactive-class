package com.amen.isa.order.service;

import com.amen.isa.component.repository.OrderRepository;
import com.amen.isa.component.repository.UserRepository;
import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreUser;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Test
    void getAll() {
        var storeOrders = List.of(StoreOrder.builder().storeId("1").id("123").build(),
                                  StoreOrder.builder().storeId("1").id("125").build(),
                                  StoreOrder.builder().storeId("2").id("127").build(),
                                  StoreOrder.builder().storeId("2").id("128").build());
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findAllByUserId(any(ObjectId.class)))
                .thenReturn(Flux.fromIterable(storeOrders));

        var user = StoreUser.builder().firstName("Marian").build();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(anyString())).thenReturn(Mono.just(
                user
        ));


        OrderService orderService = new OrderService(null, orderRepository, userRepository, null);
        var response = orderService.zipUserWithOrders("123456789012345678901234");

        StepVerifier.create(response)
//                .expectNextCount(1)
                .expectNext(Tuples.of(user, storeOrders))
                .expectComplete()
                .verify();
    }
}