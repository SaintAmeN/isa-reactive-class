package com.amen.isa.model.response;

import java.util.List;

public record OrderItemsResponse(String orderId,
                                 List<OrderItemDto> items) {
}
