package com.amen.isa.model.response;

import com.amen.isa.model.domain.StoreOrderItem;
import com.amen.isa.model.domain.StoreUser;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponse(String storeId,
                            StoreUser user,
                            Set<StoreOrderItem> items,
                            LocalDateTime created) {
}