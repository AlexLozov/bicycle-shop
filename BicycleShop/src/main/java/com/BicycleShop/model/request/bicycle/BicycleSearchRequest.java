package com.BicycleShop.model.request.bicycle;

import com.BicycleShop.model.enums.BicycleSortField;
import lombok.Data;

@Data
public class BicycleSearchRequest {
    private String name;
    private Integer price;

    private Boolean deleted;
    private String keyword;
    private BicycleSortField sortField;


}
