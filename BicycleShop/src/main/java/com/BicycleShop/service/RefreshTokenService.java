package com.BicycleShop.service;

import com.BicycleShop.model.entities.RefreshToken;
import com.BicycleShop.model.entities.User;
import jakarta.validation.constraints.NotNull;

public interface RefreshTokenService {

    RefreshToken createOrUpdateRefreshToken(@NotNull User user);

}
