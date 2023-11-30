package com.amen.isa.shipment.controller;

import com.amen.isa.model.domain.Product;
import com.amen.isa.model.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController()
@RequiredArgsConstructor
@RequestMapping("/shipment")
public class ShipmentController {

    @PostMapping()
    public Mono<Void> submit(@RequestBody List<Product> request) {
        log.info("Shipment req: {}", request);
        return Mono.empty();
    }
}
