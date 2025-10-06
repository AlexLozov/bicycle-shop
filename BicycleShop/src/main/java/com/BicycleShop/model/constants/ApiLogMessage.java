package com.BicycleShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiLogMessage {
    BICYCLE_INFO_BY_ID("Receeving bicycle with id: %s"),
    ;

    private String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
