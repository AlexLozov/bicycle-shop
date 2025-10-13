package com.BicycleShop.service;

import com.BicycleShop.model.dto.shopping_cart.ShoppingCartDTO;
import com.BicycleShop.model.request.shopping_cart.AddToShoppingCart;
import com.BicycleShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface ShoppingCartService {

    public IamResponse<ShoppingCartDTO> addToCart(@NotNull AddToShoppingCart request);
}
