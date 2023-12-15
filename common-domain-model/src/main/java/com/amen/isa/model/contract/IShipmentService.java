package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import com.amen.isa.model.domain.ProductQuantity;
import com.amen.isa.model.domain.Shipment;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IShipmentService {
    Mono<Shipment> createShipment(String storeId, List<ProductQuantity> productQuantity);
}

