package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import reactor.core.publisher.Mono;

public interface IBasketService {
    Mono<Basket> getBasket(String userId);

    Mono<Basket> addToBasket(String userId, BasketPosition position);

    Mono<Basket> removeFromBasket(String userId, int position);
}

