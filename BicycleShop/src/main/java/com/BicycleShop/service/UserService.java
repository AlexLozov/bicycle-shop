package com.BicycleShop.service;

import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import com.BicycleShop.model.dto.user.FullUserDTO;
import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.dto.user.UserSearchDTO;
import com.BicycleShop.model.request.bicycle.BicycleSearchRequest;
import com.BicycleShop.model.request.user.NewUserRequest;
import com.BicycleShop.model.request.user.UserSearchRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    IamResponse<FullUserDTO> getFullUserById(@NotNull Integer id);
    IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest);
    IamResponse<UserDTO> getUserById(@NotNull Integer id);
    void softDeleteUserById(@NotNull Integer id);
    IamResponse<UserDTO> updateUserById(@NotNull Integer id, @NotNull NewUserRequest newUserRequest);

    IamResponse<PaginationResponse<UserSearchDTO>> findAllUsers(Pageable pageable);
    IamResponse<PaginationResponse<UserSearchDTO>> searchUsers(@NotNull UserSearchRequest request, Pageable pageable);
}
