package com.library.Library.repository;

import com.library.Library.entity.CheckoutCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutCartRepository extends JpaRepository<CheckoutCart, String> {

}
