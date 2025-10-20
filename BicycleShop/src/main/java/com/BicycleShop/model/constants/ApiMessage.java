package com.BicycleShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiMessage {
    TOKEN_CREATED_OR_UPDATED("User's token has been updated or created"),
    ;

    private final String message;



}
