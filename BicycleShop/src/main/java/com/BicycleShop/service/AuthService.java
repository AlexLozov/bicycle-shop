package com.BicycleShop.service;

import com.BicycleShop.model.dto.user.LoginRequest;
import com.BicycleShop.model.dto.user.UserProfileDTO;
import com.BicycleShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface AuthService {

    IamResponse<UserProfileDTO> loginUser(@NotNull LoginRequest request);
}
