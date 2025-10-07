package com.BicycleShop.model.request.bicycle;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BicycleRequest implements Serializable {

    private String brand;
    private String type;
    private Float price;
    private Integer stock;
    private String description;
    private String imageUrl;

}
