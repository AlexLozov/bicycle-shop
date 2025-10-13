package com.BicycleShop.model.dto.shopping_cart;

import com.BicycleShop.model.dto.cart_item.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO implements Serializable {
    private Integer id;
    private List<CartItemDTO> items;
}
