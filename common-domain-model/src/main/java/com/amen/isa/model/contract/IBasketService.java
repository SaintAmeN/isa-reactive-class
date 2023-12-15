package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;

public interface IBasketService {
    Basket getBasket(String userId);

    Basket addToBasket(String userId, BasketPosition position);

    Basket removeFromBasket(String userId, int position);
}

