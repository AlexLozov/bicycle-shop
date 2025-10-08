package com.BicycleShop.model.dto.bicycle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BicycleSearchDTO implements Serializable {
    private Integer id;
    private String name;
    private String brand;
    private String type;
    private Float price;
    private Integer stock;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private boolean deleted;

}
