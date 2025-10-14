package com.BicycleShop.model.dto.user;

import com.BicycleShop.model.dto.shopping_cart.ShoppingCartDTO;
import com.BicycleShop.model.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullUserDTO implements Serializable {
    private Integer id;
    private String username;
    private String email;
    private LocalDateTime created;
    private LocalDateTime lastLogin;

    private RegistrationStatus registrationStatus;

    private ShoppingCartDTO shoppingCart;
}
