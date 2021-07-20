package com.library.Library.repository;

import com.library.Library.entity.AppUser;
import com.library.Library.entity.Book;
import com.library.Library.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findById(String id);
    List<Cart> findCartsByAppUser(AppUser appUser);
    Cart findCartByBook(Book book);

    void deleteByAppUser(AppUser appUser);
    void deleteCartByBook(Book book);
    boolean existsById(String id);
}
