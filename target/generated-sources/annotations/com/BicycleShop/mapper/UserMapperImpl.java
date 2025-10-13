package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.enums.RegistrationStatus;
import com.BicycleShop.model.request.user.NewUserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-13T22:23:43+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setLastLogin( user.getLast_login() );
        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setCreated( user.getCreated() );
        userDTO.setRegistrationStatus( user.getRegistrationStatus() );

        userDTO.setShoppingCart( mapShoppingCart(user.getShoppingCart()) );

        return userDTO;
    }

    @Override
    public User createUser(NewUserRequest newUserRequest) {
        if ( newUserRequest == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( newUserRequest.getUsername() );
        user.setPassword( newUserRequest.getPassword() );
        user.setEmail( newUserRequest.getEmail() );

        user.setRegistrationStatus( RegistrationStatus.ACTIVE );

        return user;
    }
}
