package com.BicycleShop.repositories;

import com.BicycleShop.model.entities.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByIdAndDeletedFalse(Integer id);
    boolean existsByEmailAndIdNot(String email,Integer id);
    boolean existsByUsernameAndIdNot( String username, Integer id);


}
