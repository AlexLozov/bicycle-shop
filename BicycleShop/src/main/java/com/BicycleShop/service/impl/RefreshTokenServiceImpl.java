package com.BicycleShop.service.impl;

import com.BicycleShop.model.entities.RefreshToken;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.repositories.RefreshTokenRepository;
import com.BicycleShop.service.RefreshTokenService;
import com.BicycleShop.utils.ApiUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createOrUpdateRefreshToken(@NotNull User user) {
        return refreshTokenRepository.findByUserId(user.getId())
                .map(refreshToken -> {
                    refreshToken.setCreated(LocalDateTime.now());
                    refreshToken.setToken(ApiUtils.generateUuidWithoutDash());
                    return refreshTokenRepository.save(refreshToken);
                })
                .orElseGet(() -> {
                    RefreshToken refreshToken = new RefreshToken();
                    refreshToken.setCreated(LocalDateTime.now());
                    refreshToken.setToken(ApiUtils.generateUuidWithoutDash());
                    refreshToken.setUser(user);
                    return refreshTokenRepository.save(refreshToken);
                });
    }
}
