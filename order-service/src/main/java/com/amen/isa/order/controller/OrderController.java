package com.amen.isa.order.controller;

import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.request.StoreOrderRequest;
import com.amen.isa.model.response.OrderResponse;
import com.amen.isa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController()
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public Flux<OrderResponse> getAll() {
        return orderService.getAll();
    }

    @PostMapping()
    public Mono<StoreOrder> add(@RequestBody StoreOrderRequest request) {
        return orderService.add(request);
    }
}
