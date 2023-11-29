package com.amen.isa.model.request;


import com.amen.isa.model.domain.StoreOrderItem;

import java.util.Set;

public record StoreOrderRequest(String userId, Set<StoreOrderItem> items) {
}
