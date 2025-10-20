package com.BicycleShop.service.impl;

import com.BicycleShop.mapper.UserMapper;
import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.dto.user.LoginRequest;
import com.BicycleShop.model.dto.user.UserProfileDTO;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.exception.InvalidDataException;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.repositories.UserRepository;
import com.BicycleShop.security.JwtTokenProvider;
import com.BicycleShop.service.AuthService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

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

        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user, token);
        userProfileDTO.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileDTO);
    }
}
