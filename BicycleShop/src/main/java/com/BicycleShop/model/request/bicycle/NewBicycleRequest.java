package com.BicycleShop.model.request.bicycle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBicycleRequest implements Serializable {
    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "brand cannot be empty")
    private String brand;

    @NotBlank(message = "type cannot be empty")
    private String type;

    @NotNull(message = "price cannot be empty")
    private Float price;

    @NotNull(message = "stock cannot be empty")
    private Integer stock;

    @NotBlank(message = "description cannot be empty")
    private String description;

    @NotBlank(message = "imageUrl cannot be empty")
    private String imageUrl;

}
