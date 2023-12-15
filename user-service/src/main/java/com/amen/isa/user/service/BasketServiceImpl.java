package com.amen.isa.user.service;

import com.amen.isa.model.contract.IBasketService;
import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import org.springframework.stereotype.Service;

// TODO: Write me :)
@Service
public class BasketServiceImpl implements IBasketService {
    @Override
    public Basket getBasket(String userId) {
        return null;
    }

    @Override
    public Basket addToBasket(String userId, BasketPosition position) {
        return null;
    }

    @Override
    public Basket removeFromBasket(String userId, int position) {
        return null;
    }
}
