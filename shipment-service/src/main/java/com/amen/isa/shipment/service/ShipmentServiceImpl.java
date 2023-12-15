package com.amen.isa.shipment.service;

import com.amen.isa.model.contract.IShipmentService;
import com.amen.isa.model.domain.ProductQuantity;
import com.amen.isa.model.domain.Shipment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

// TODO: Write me :)
@Service
public class ShipmentServiceImpl implements IShipmentService {
    @Override
    public Mono<Shipment> createShipment(String storeId, List<ProductQuantity> productQuantity) {
        return null;
    }
}
