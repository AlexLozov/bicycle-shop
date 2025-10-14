package com.BicycleShop.repositories;

import com.BicycleShop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByIdAndDeletedFalse(Integer id);


}
