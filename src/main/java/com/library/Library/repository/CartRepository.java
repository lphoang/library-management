package com.library.Library.repository;

import com.library.Library.entity.AppUser;
import com.library.Library.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findById(String id);
    void deleteByAppUser(AppUser appUser);
    boolean existsById(String id);
    boolean existsByAppUser(AppUser appUser);
}
