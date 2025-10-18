package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.cart_item.CartItemDTO;
import com.BicycleShop.model.dto.role.RoleDTO;
import com.BicycleShop.model.dto.shopping_cart.ShoppingCartDTO;
import com.BicycleShop.model.dto.user.FullUserDTO;
import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.dto.user.UserSearchDTO;
import com.BicycleShop.model.entities.Role;
import com.BicycleShop.model.entities.ShoppingCart;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.enums.RegistrationStatus;
import com.BicycleShop.model.request.user.NewUserRequest;
import com.BicycleShop.model.request.user.UserSearchRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {RegistrationStatus.class, Object.class}
)
public interface UserMapper {

    @Mapping(source = "last_login", target = "lastLogin")
    @Mapping(target = "shoppingCart", expression = "java(mapShoppingCart(user.getShoppingCart()))")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    FullUserDTO toFullDTO(User user);

    // --- метод для маппинга корзины ---
    default ShoppingCartDTO mapShoppingCart(ShoppingCart cart) {
        if (cart == null) return null;

        ShoppingCartDTO cartDTO = new ShoppingCartDTO();
        cartDTO.setId(cart.getId());

        cartDTO.setItems(
                cart.getItems().stream().map(item -> {
                    CartItemDTO itemDTO = new CartItemDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setName(item.getBicycle().getName());
                    itemDTO.setBrand(item.getBicycle().getBrand());
                    itemDTO.setType(item.getBicycle().getType());
                    itemDTO.setPrice(item.getBicycle().getPrice().floatValue());
                    itemDTO.setDescription(item.getBicycle().getDescription());
                    itemDTO.setImageUrl(item.getBicycle().getImageUrl());
                    itemDTO.setQuantity(item.getQuantity());
                    return itemDTO;
                }).toList()
        );

        return cartDTO;
    }



    @Mapping(source = "last_login", target = "lastLogin")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserDTO toDTO(User user);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "registrationStatus", expression = "java(RegistrationStatus.ACTIVE)")
    User createUser(NewUserRequest newUserRequest);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserSearchDTO toUserSearchDTO(User user);


    default List<RoleDTO> mapRoles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .toList();
    }

}
