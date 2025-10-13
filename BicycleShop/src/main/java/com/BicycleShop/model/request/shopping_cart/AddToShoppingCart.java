package com.BicycleShop.model.request.shopping_cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToShoppingCart {
    @NotNull(message = "userId cannot be empty")
    private Integer userId;

    @NotNull(message = "bicycleId cannot be empty")
    private Integer bicycleId;

    @NotNull(message = "quantity cannot be empty")
    private Integer quantity;
}
