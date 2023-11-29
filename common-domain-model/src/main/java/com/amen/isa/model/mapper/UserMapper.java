package com.amen.isa.model.mapper;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.model.request.AddUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        imports = {Basket.class})
public interface UserMapper {

    @Mapping(target = "userId", expression = "java(null)")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "basket", expression = "java(Basket.empty())")
    StoreUser addUserRequestToStoreUser(AddUserRequest request);
}
