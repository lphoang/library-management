package com.library.Library.service;

import com.library.Library.dto.requests.AddItemRequest;
import com.library.Library.dto.requests.RemoveItemRequest;
import com.library.Library.entity.AppUser;
import com.library.Library.entity.Book;
import com.library.Library.entity.Cart;
import com.library.Library.repository.AppUserRepository;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final AppUserRepository appUserRepository;
    private final BookRepository bookRepository;

    @Transactional
    public ResponseEntity<Cart> addItem(AddItemRequest request) {
        AppUser appUser = appUserRepository.findById(request.getAppUserId()).get();
        Book book = bookRepository.findBookById(request.getBookId()).get();
        Set<Cart> carts = appUser.getCarts();
        Cart cart;
        if (carts.isEmpty() || request.getCartId() == null) {
            carts = new HashSet<>();
            Set<Book> items = new HashSet<>();
            items.add(book);
            cart = new Cart(
                    items,
                    appUser,
                    book.getPrice(),
                    LocalDateTime.now()
            );
            carts.add(cart);
            return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.CREATED);
        } else {
            cart = cartRepository.findById(request.getCartId()).get();
            if (cart.getItems().contains(book)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The book is already in cart!");
            }
            cart.getItems().add(book);
            cart.setTotal(cart.getTotal() + book.getPrice());
            cart.setUpdatedAt(LocalDateTime.now());
            return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
        }
    }

    @Transactional
    public void removeItem(RemoveItemRequest request) {
        AppUser appUser = appUserRepository.findById(request.getAppUserId()).get();
        boolean isCartExists = cartRepository.existsByAppUser(appUser);
        Set<Cart> carts = appUser.getCarts();
        if (isCartExists) {
            Cart cart = cartRepository.findById(request.getCartId()).get();
            Set<Book> items = cart.getItems();
            Book book = bookRepository.findBookById(request.getBookId()).get();
            if (items.contains(book)) {
                items.remove(book);
                cart.setUpdatedAt(LocalDateTime.now());
                cart.setTotal(cart.getTotal() - book.getPrice());
                cartRepository.save(cart);
                if (items.isEmpty()) {
                    cartRepository.deleteByAppUser(appUser);
                    carts.remove(cart);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no book found in cart");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no cart found");
        }
    }

    @Transactional
    public ResponseEntity<Cart> payCart(String appUserId, String cartId) {
        if (appUserRepository.findById(appUserId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            AppUser appUser = appUserRepository.findById(appUserId).get();
            if (cart.getItems().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have any items in cart!");
            } else if (cart.getIsPaid() == false) {
                cart.setUpdatedAt(LocalDateTime.now());
                cart.setPaidAt(LocalDateTime.now());
                cart.setIsPaid(true);
                Cart newCart = new Cart(
                        appUser,
                        0.0,
                        LocalDateTime.now()
                );
                cartRepository.save(newCart);
                return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You paid the cart!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user found!");
        }
    }
}
