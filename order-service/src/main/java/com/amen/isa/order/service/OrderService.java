package com.amen.isa.order.service;

import com.amen.isa.component.repository.OrderRepository;
import com.amen.isa.component.repository.UserRepository;
import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.mapper.OrderMapper;
import com.amen.isa.model.request.StoreOrderRequest;
import com.amen.isa.model.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    // Zip User+Order+Shipment

    public Flux<OrderResponse> getAll() {
        var orders = orderRepository.findAll();


        return orders.flatMap(order ->{
            var users = userRepository.findAll();

            return users
                    .filter(storeUser -> storeUser.getUserId() == order.getUser().getUserId())
                    .map(storeUser -> new OrderResponse(
                            order.getStoreId(),
                            storeUser,
                            order.getItems(),
                            order.getCreated()
                    ));
        });
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
