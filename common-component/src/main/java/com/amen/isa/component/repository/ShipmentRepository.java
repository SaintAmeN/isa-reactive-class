package com.amen.isa.component.repository;

import com.amen.isa.model.domain.Shipment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ShipmentRepository extends ReactiveMongoRepository<Shipment, String> {
}
