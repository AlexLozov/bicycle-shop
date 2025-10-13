package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.enums.RegistrationStatus;
import com.BicycleShop.model.request.user.NewUserRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {RegistrationStatus.class, Object.class}
)
public interface UserMapper {

    @Mapping(source = "last_login", target = "lastLogin")
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "registrationStatus", expression = "java(RegistrationStatus.ACTIVE)")
    User createUser(NewUserRequest newUserRequest);


}
