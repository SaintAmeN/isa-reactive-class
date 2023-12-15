package com.amen.isa.model.contract;

import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreOrderItem;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface IStoreOrderService {
    Mono<StoreOrder> placeOrder(String storeId, ObjectId userId, Set<StoreOrderItem> items);
}

