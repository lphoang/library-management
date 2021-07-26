package com.library.Library.service.impl;

import com.library.Library.dto.requests.CheckoutRequest;
import com.library.Library.entity.*;
import com.library.Library.repository.AppUserRepository;
import com.library.Library.repository.CheckoutCartRepository;
import com.library.Library.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CheckoutCartService {
    private final CheckoutCartRepository checkoutCartRepository;
    private final OrderRepository orderRepository;
    private final AppUserRepository appUserRepository;

    @Transactional
    public ResponseEntity<CheckoutCart> checkOutCart(CheckoutRequest request) {
        Optional<OrderDetail> orderDetail = orderRepository.findById(request.getOrderId());
        Optional<AppUser> appUser = appUserRepository.findById(orderDetail.get().getAppUser().getId());
        if (!appUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was no user found");
        } else {
            if (!orderDetail.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was no order found");
            } else {
                if (orderDetail.get().getIsPaid()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order was already paid!");
                } else {
                    if (!orderDetail.get().getCartList().isEmpty()) {
                        CheckoutCart checkoutCart = new CheckoutCart(
                                LocalDateTime.now(),
                                orderDetail.get().getAppUser(),
                                orderDetail.get(),
                                request.getPaymentType(),
                                request.getDeliveryAddress()
                        );
                        orderDetail.get().setIsPaid(true);
                        return new ResponseEntity<>(checkoutCartRepository.save(checkoutCart), HttpStatus.OK);
                    }else{
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
                }
            }
        }
    }
}
}
