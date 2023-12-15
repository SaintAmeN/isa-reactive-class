package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreOrderItem;
import org.bson.types.ObjectId;

import java.util.Set;

public interface IStoreOrderService {
    StoreOrder placeOrder(String storeId, ObjectId userId, Set<StoreOrderItem> items);
}

