package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.shopping_cart.ShoppingCartDTO;
import com.BicycleShop.model.entities.ShoppingCart;

public interface ShoppingCartMapper {

    public ShoppingCartDTO toShoppingCartDTO(ShoppingCart shoppingCart);
}
