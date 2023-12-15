package com.amen.isa.model.domain;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "store.user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Report {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String storeId;

    @CreationTimestamp
    private LocalDateTime created;

    // New properties for assessing sales and calculating margins
    private double totalSales;
    private double totalCost;
    private double totalProfitMargin;

    // Other properties related to order statistics
    private int totalOrders;
    private int successfulOrders;
    private int failedOrders;


}
