package com.library.Library.service.impl;

import com.library.Library.dto.requests.AddItemRequest;
import com.library.Library.dto.requests.RemoveItemRequest;
import com.library.Library.entity.AppUser;
import com.library.Library.entity.Book;
import com.library.Library.entity.Cart;
import com.library.Library.entity.OrderDetail;
import com.library.Library.repository.AppUserRepository;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.CartRepository;
import com.library.Library.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CartService {
    private final CartRepository cartRepository;
    private final AppUserRepository appUserRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final static Logger LOGGER = LoggerFactory
            .getLogger(CartService.class);

    @Transactional
    public ResponseEntity<Cart> addItem(AddItemRequest request) {
        AppUser appUser = appUserRepository.findById(request.getAppUserId()).get();
        Book book = bookRepository.findBookById(request.getBookId()).get();
        Optional<OrderDetail> orderDetail
                = orderRepository.findOrderDetailByAppUserAndIsPaid(appUser, false);
        Cart cart;
        if (!orderDetail.isPresent()) {
            List<Cart> newCartList = new ArrayList<>();
            OrderDetail newOrder = new OrderDetail(
                    newCartList,
                    appUser
            );
            cart = new Cart(
                    book,
                    1,
                    newOrder,
                    LocalDateTime.now(),
                    book.getPrice()
            );
            orderRepository.save(newOrder);
            return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
        } else {
            List<Cart> cartList = orderDetail.get().getCartList();
            Boolean isBookExists = cartRepository.existsByOrderDetailAndBook(orderDetail.get(), book);
            if (!cartList.isEmpty()) {
                if (isBookExists) {
                    cart = cartRepository.findCartByOrderDetailAndBook(orderDetail.get(), book);
                    cart.setQuantity(cart.getQuantity() + 1);
                    cart.setTotal(cart.getTotal() + book.getPrice());
                } else {
                    cart = new Cart(
                            book,
                            1,
                            orderDetail.get(),
                            LocalDateTime.now(),
                            book.getPrice()
                    );
                    return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
                }
            } else {
                cart = new Cart(
                        book,
                        1,
                        orderDetail.get(),
                        LocalDateTime.now(),
                        book.getPrice()
                );
                return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
            }
            return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
        }
    }

//    @Transactional
//    public ResponseEntity<List<Cart>> getCartsByUser(String id) {
//        AppUser appUser = appUserRepository.findById(id).get();
//        return new ResponseEntity<>(cartRepository.findCartsByAppUser(appUser), HttpStatus.OK);
//    }

    @Transactional
    public void removeItem(RemoveItemRequest request) {
        AppUser appUser = appUserRepository.findById(request.getAppUserId()).get();
        Book book = bookRepository.findBookById(request.getBookId()).get();
        Optional<OrderDetail> orderDetail
                = orderRepository.findOrderDetailByAppUserAndIsPaid(appUser, false);
        if (orderDetail.isPresent()) {
            List<Cart> cartList = orderDetail.get().getCartList();
            if (!cartList.isEmpty()) {
                Cart cart = cartRepository.findCartByOrderDetailAndBook(orderDetail.get(), book);
                if (cartList.contains(cart)) {
                    cart.setQuantity(cart.getQuantity() - 1);
                    cart.setTotal(cart.getTotal() - book.getPrice());
                    if (cart.getQuantity() == 0 || cart.getTotal() == 0) {
                        cartRepository.deleteCartByOrderDetailAndBook(orderDetail.get(), book);
                    }
                } else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was no " + book.getTitle() + "'s book in cart");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was no book found in cart");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was no order found");
        }
    }
}
