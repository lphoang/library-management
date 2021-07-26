package com.library.Library.repository;

import com.library.Library.entity.AppUser;
import com.library.Library.entity.Book;
import com.library.Library.entity.Cart;
import com.library.Library.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findById(String id);
    Cart findCartByOrderDetailAndBook(OrderDetail orderDetail, Book book);
    Boolean existsByOrderDetailAndBook(OrderDetail orderDetail, Book book);

    void deleteCartByOrderDetailAndBook(OrderDetail orderDetail, Book book);
    boolean existsById(String id);
}
