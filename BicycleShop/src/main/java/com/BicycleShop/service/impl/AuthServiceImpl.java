package com.BicycleShop.service.impl;

import com.BicycleShop.mapper.UserMapper;
import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.entities.Role;
import com.BicycleShop.model.entities.ShoppingCart;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.request.user.LoginRequest;
import com.BicycleShop.model.dto.user.UserProfileDTO;
import com.BicycleShop.model.entities.RefreshToken;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.exception.InvalidDataException;
import com.BicycleShop.model.request.user.RegistrationUserRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.repositories.RoleRepository;
import com.BicycleShop.repositories.UserRepository;
import com.BicycleShop.security.JwtTokenProvider;
import com.BicycleShop.security.validator.AccessValidator;
import com.BicycleShop.service.AuthService;
import com.BicycleShop.service.RefreshTokenService;
import com.BicycleShop.service.model.IamServiceUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessValidator accessValidator;

    @Override
    public IamResponse<UserProfileDTO> loginUser(@NotNull LoginRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new InvalidDataException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage());
        }

        User user = userRepository.findUserByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new InvalidDataException(ApiErrorMessage.USER_WITH_EMAIL_NOT_FOUND.getMessage(request.getEmail())));

        String token = jwtTokenProvider.generateToken(user);

        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user, token, refreshToken.getToken());
        userProfileDTO.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileDTO);
    }



    @Override
    public IamResponse<UserProfileDTO> refreshAccessToken(@NotNull String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validateOrUpdateRefreshToken(refreshTokenValue);
        User user = refreshToken.getUser();

        String token = jwtTokenProvider.generateToken(user);
        return IamResponse.createSuccessfulWithNewToken(
                userMapper.toUserProfileDTO(user, token ,refreshToken.getToken())
        );
    }



    @Override
    public IamResponse<UserProfileDTO> registerUser(@NotNull RegistrationUserRequest request) {
        accessValidator.validateNewUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword());

        Role userRole = roleRepository.findByName(IamServiceUserRole.USER.getRole())
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.ROLE_WITH_NAME_NOT_FOUND.getMessage(IamServiceUserRole.USER.getRole())));

        User newUser = userMapper.fromDto(request);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(newUser);
        newUser.setShoppingCart(cart);

        userRepository.save(newUser);

        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(newUser);
        String token = jwtTokenProvider.generateToken(newUser);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(newUser, token, refreshToken.getToken());
        userProfileDTO.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileDTO);
    }
}
