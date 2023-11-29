package com.amen.isa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "store.user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreUser {

    @Id
    @EqualsAndHashCode.Include
    private String userId;

    private String firstName;
    private String lastName;

    private Basket basket;
}
