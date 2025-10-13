package com.BicycleShop.mapper.Impl;

import com.BicycleShop.mapper.ShoppingCartMapper;
import com.BicycleShop.model.dto.cart_item.CartItemDTO;
import com.BicycleShop.model.dto.shopping_cart.ShoppingCartDTO;
import com.BicycleShop.model.entities.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapperImpl implements ShoppingCartMapper {

    public ShoppingCartDTO toShoppingCartDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setItems(shoppingCart.getItems().stream().map(item ->{
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(item.getId());
            cartItemDTO.setQuantity(item.getQuantity());
            cartItemDTO.setBrand(item.getBicycle().getBrand());
            cartItemDTO.setName(item.getBicycle().getName());
            cartItemDTO.setPrice(item.getBicycle().getPrice());
            cartItemDTO.setType(item.getBicycle().getType());
            cartItemDTO.setDescription(item.getBicycle().getDescription());
            cartItemDTO.setImageUrl(item.getBicycle().getImageUrl());
            return cartItemDTO;
        }).toList());
        return shoppingCartDTO;
    }

}
