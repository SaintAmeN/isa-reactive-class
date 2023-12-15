package com.amen.isa.order.service;

import com.amen.isa.model.contract.IStoreOrderService;
import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreOrderItem;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

// TODO: Write me :)
@Service
public class StoreOrderServiceImpl implements IStoreOrderService {
    @Override
    public Mono<StoreOrder> placeOrder(String storeId, ObjectId userId, Set<StoreOrderItem> items) {
        return null;
    }
}