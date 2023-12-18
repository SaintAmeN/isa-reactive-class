package com.amen.isa.user.controller;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import com.amen.isa.user.service.BasketServiceImpl;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static com.amen.isa.component.configuration.Constants.REQUEST_ID;
import static com.amen.isa.component.configuration.Constants.REQUEST_ID_HEADER_NAME;

// TODO: Write me :)
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basket")
public class BasketController {
    // Inject BasketService and implement REST endpoints
    private final BasketServiceImpl basketService;

    @GetMapping("/{userId}")
    public Mono<Basket> getBasket(@PathVariable String userId) {
        return basketService.getBasket(userId);
    }

//    @PostMapping("/{userId}")
//    public Mono<Basket> addToBasket(@PathVariable String userId, @RequestBody BasketPosition position) {
//        return basketService.addToBasket(userId, position);
//    }

    @PostMapping("/{userId}")
    public Mono<Basket> addToBasket(@PathVariable String userId, @RequestParam long productId) {
        var traceId = Span.current().getSpanContext().getTraceId();
        log.info("Trace start: {}", traceId);

        return basketService.addToBasket(userId, productId)
                .contextWrite(Context.of(REQUEST_ID, traceId));
    }

    @DeleteMapping("/{userId}")
    public Mono<Basket> removeFromBasket(@PathVariable String userId, @RequestBody int position) {
        return basketService.removeFromBasket(userId, position);
    }
}