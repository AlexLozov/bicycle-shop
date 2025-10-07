package com.BicycleShop.repositories;

import com.BicycleShop.model.entities.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BicycleRepository extends JpaRepository<Bicycle, Integer> {

    boolean existsByName(String name);
    Optional<Bicycle> findByIdAndDeletedFalse(Integer id);

}
