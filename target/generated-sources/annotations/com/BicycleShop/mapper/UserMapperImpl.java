package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.user.FullUserDTO;
import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.dto.user.UserProfileDTO;
import com.BicycleShop.model.dto.user.UserSearchDTO;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.enums.RegistrationStatus;
import com.BicycleShop.model.request.user.NewUserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-21T12:50:35+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public FullUserDTO toFullDTO(User user) {
        if ( user == null ) {
            return null;
        }

        FullUserDTO fullUserDTO = new FullUserDTO();

        fullUserDTO.setLastLogin( user.getLast_login() );
        fullUserDTO.setId( user.getId() );
        fullUserDTO.setUsername( user.getUsername() );
        fullUserDTO.setEmail( user.getEmail() );
        fullUserDTO.setCreated( user.getCreated() );
        fullUserDTO.setRegistrationStatus( user.getRegistrationStatus() );

        fullUserDTO.setShoppingCart( mapShoppingCart(user.getShoppingCart()) );
        fullUserDTO.setRoles( mapRoles(user.getRoles()) );

        return fullUserDTO;
    }

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

        userDTO.setRoles( mapRoles(user.getRoles()) );

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

    @Override
    public UserSearchDTO toUserSearchDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserSearchDTO userSearchDTO = new UserSearchDTO();

        userSearchDTO.setId( user.getId() );
        userSearchDTO.setUsername( user.getUsername() );
        userSearchDTO.setPassword( user.getPassword() );
        userSearchDTO.setEmail( user.getEmail() );
        userSearchDTO.setCreated( user.getCreated() );
        userSearchDTO.setUpdated( user.getUpdated() );
        userSearchDTO.setLast_login( user.getLast_login() );
        userSearchDTO.setDeleted( user.getDeleted() );
        userSearchDTO.setRegistrationStatus( user.getRegistrationStatus() );

        userSearchDTO.setRoles( mapRoles(user.getRoles()) );

        return userSearchDTO;
    }

    @Override
    public UserProfileDTO toUserProfileDTO(User user, String token, String refreshToken) {
        if ( user == null && token == null && refreshToken == null ) {
            return null;
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();

        if ( user != null ) {
            userProfileDTO.setUsername( user.getUsername() );
            userProfileDTO.setEmail( user.getEmail() );
            userProfileDTO.setId( user.getId() );
            userProfileDTO.setRegistrationStatus( user.getRegistrationStatus() );
            userProfileDTO.setLast_login( user.getLast_login() );
        }
        userProfileDTO.setToken( token );
        userProfileDTO.setRefreshToken( refreshToken );
        userProfileDTO.setRoles( mapRoles(user.getRoles()) );

        return userProfileDTO;
    }
}
