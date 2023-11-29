package com.amen.isa.model.mapper;

import com.amen.isa.model.domain.Product;
import com.amen.isa.model.request.AddProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "name", source = "name")
    Product addProductRequestToProduct(AddProductRequest request);
}
