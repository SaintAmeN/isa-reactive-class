package com.amen.isa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketPosition {
    private int position;

    @DocumentReference
    private Product product;

    private ProductQuantity productQuantity;
}
