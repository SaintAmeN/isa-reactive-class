package com.amen.isa.order.service;

import com.amen.isa.component.repository.OrderRepository;
import com.amen.isa.component.repository.UserRepository;
import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.model.mapper.OrderMapper;
import com.amen.isa.model.request.StoreOrderRequest;
import com.amen.isa.model.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    // zip -> (O1, U5), (O2, U6), (O3, U9)
    // Zip (User + List<Order>)
    public Flux<Tuple2<StoreUser, List<StoreOrder>>> zipUserWithOrders(String userId) {
        var user = userRepository.findById(userId);
        var orders = orderRepository.findAllByUserId(new ObjectId(userId));

        return Flux.zip(user, orders.collectList());
    }

    public Flux<OrderResponse> getAll() {
        var orders = orderRepository.findAll();     // O1 , O2 , O3
        var users = userRepository.findAll();       // U5 , U6 , U9

        return orders.flatMap(order -> users
                .filter(storeUser -> storeUser.getUserId() == order.getUser().getUserId())
                .map(storeUser -> new OrderResponse(
                        order.getStoreId(),
                        storeUser,
                        order.getItems(),
                        order.getCreated()
                )));
    }


//    public Mono<String> getAll() {
////
//    }

    public Mono<StoreOrder> add(final StoreOrderRequest request) {
        return userRepository.findById(request.userId())
                .flatMap(storeUser -> {
                             var storeOrder = orderMapper.requestToStoreOrder(request, storeUser);
                             return orderRepository.save(storeOrder);
                         }
                );

    }

}
