package com.amen.isa.model.domain;

import lombok.Builder;

@Builder
public record StoreOrderItem(Long productId, ProductQuantity quantity) {
}
