package com.BicycleShop.repositories;

import com.BicycleShop.model.entities.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BicycleRepository extends JpaRepository<Bicycle, Integer>, JpaSpecificationExecutor<Bicycle> {

    boolean existsByName(String name);
    Optional<Bicycle> findByIdAndDeletedFalse(Integer id);

}
