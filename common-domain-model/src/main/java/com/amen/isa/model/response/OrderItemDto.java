package com.amen.isa.model.response;

import com.amen.isa.model.domain.ProductQuantity;

public record OrderItemDto(Long productId,
                           String productName,
                           ProductQuantity quantity) {
}
