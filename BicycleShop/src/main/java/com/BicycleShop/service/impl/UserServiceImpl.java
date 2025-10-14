package com.BicycleShop.service.impl;

import com.BicycleShop.mapper.UserMapper;
import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.dto.user.FullUserDTO;
import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.entities.ShoppingCart;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.exception.DataExistException;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.request.user.NewUserRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.repositories.ShoppingCartRepository;
import com.BicycleShop.repositories.UserRepository;
import com.BicycleShop.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserMapper userMapper;

    @Override
    public IamResponse<FullUserDTO> getFullUserById(@NotNull Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        FullUserDTO fullUserDTO = userMapper.toFullDTO(user);
        return IamResponse.createSuccessful(fullUserDTO);
    }

    @Override
    public IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest) {
        if(userRepository.existsByUsername(newUserRequest.getUsername())) {
            throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage(newUserRequest.getUsername()));
        }

        if(userRepository.existsByEmail(newUserRequest.getEmail())) {
            throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage(newUserRequest.getEmail()));
        }

        User user = userMapper.createUser(newUserRequest);

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        user.setShoppingCart(cart);

        userRepository.save(user);

        UserDTO userDTO = userMapper.toDTO(user);

        return IamResponse.createSuccessful(userDTO);

    }



    @Override
    public IamResponse<UserDTO> getUserById(@NotNull Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        UserDTO userDTO = userMapper.toDTO(user);
        return IamResponse.createSuccessful(userDTO);
    }
}
