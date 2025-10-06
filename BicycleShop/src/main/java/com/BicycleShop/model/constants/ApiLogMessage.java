package com.BicycleShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiLogMessage {
    BICYCLE_INFO_BY_ID("Receeving bicycle with id: {}"),
    NAME_OF_CURRENT_METHOD("Current method: {}"),
    ;

    private String value;


}
