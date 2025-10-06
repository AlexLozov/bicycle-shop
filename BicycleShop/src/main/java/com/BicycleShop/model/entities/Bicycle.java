package com.BicycleShop.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bicycles")
@Getter
@Setter
public class Bicycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String brand;

    @Column(nullable = false)
    public String type;

    @Column(nullable = false)
    public Float price;

    @Column(nullable = false)
    public Integer stock;

    @Column(nullable = false)
    public String description;

    @Column(name = "image_url", nullable = false)
    public String imageUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt = LocalDateTime.now();


}
