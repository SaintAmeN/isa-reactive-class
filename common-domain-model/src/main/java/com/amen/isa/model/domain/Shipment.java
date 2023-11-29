package com.amen.isa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shipment")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Shipment {

    @Id
    @EqualsAndHashCode.Include
    private String shipmentId;

    @CreatedDate
    private LocalDateTime created;

    // This is filled by us, not generated
    private String storeId;

    private List<ProductQuantity> productQuantity;

}
