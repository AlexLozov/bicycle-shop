package com.BicycleShop.service;

import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.request.user.NewUserRequest;
import com.BicycleShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface UserService {

    IamResponse<UserDTO> getById(@NotNull Integer id);
    IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest);
}
