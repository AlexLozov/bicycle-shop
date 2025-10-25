package com.BicycleShop.service;

import com.BicycleShop.model.request.user.LoginRequest;
import com.BicycleShop.model.dto.user.UserProfileDTO;
import com.BicycleShop.model.request.user.RegistrationUserRequest;
import com.BicycleShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface AuthService {

    IamResponse<UserProfileDTO> loginUser(@NotNull LoginRequest request);
    IamResponse<UserProfileDTO> refreshAccessToken(@NotNull String refreshToken);
    IamResponse<UserProfileDTO> registerUser(@NotNull RegistrationUserRequest request);
}
