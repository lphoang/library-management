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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final AppUserRepository appUserRepository;
    private final BookRepository bookRepository;
    private final static Logger LOGGER = LoggerFactory
            .getLogger(CartService.class);

    @Transactional
    public ResponseEntity<Cart> addItem(AddItemRequest request) {
        AppUser appUser = appUserRepository.findById(request.getAppUserId()).get();
        Book book = bookRepository.findBookById(request.getBookId()).get();
        List<Cart> cartList = cartRepository.findCartsByAppUser(appUser);
        Boolean isBookExists = cartRepository.existsByBookAndAppUser(book, appUser);
        Cart cart;
        if (!cartList.isEmpty()) {
            if (isBookExists) {
                LOGGER.error("Here");
                cart = cartRepository.findCartByBookAndAppUser(book, appUser);
                cart.setQuantity(cart.getQuantity() + 1);
            } else {
                cart = new Cart(
                        book,
                        1,
                        appUser,
                        LocalDateTime.now()
                );
                return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
            }
        } else {
            cart = new Cart(
                    book,
                    1,
                    appUser,
                    LocalDateTime.now()
            );
        }
        return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<Cart>> getCartsByUser(String id) {
        AppUser appUser = appUserRepository.findById(id).get();
        return new ResponseEntity<>(cartRepository.findCartsByAppUser(appUser), HttpStatus.OK);
    }

    @Transactional
    public void removeItem(RemoveItemRequest request) {
        AppUser appUser = appUserRepository.findById(request.getAppUserId()).get();
        List<Cart> cartList = cartRepository.findCartsByAppUser(appUser);
        Book book = bookRepository.findBookById(request.getBookId()).get();
        if (!cartList.isEmpty()) {
            Cart cart = cartRepository.findCartByBookAndAppUser(book, appUser);
            if (cartList.contains(cart)) {
                cart.setQuantity(cart.getQuantity() - 1);
                if (cart.getQuantity() == 0) {
                    cartRepository.deleteCartByBook(book);
                }
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + book.getTitle() + "'s book in cart");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no book found in cart");
        }
    }

    @Transactional
    public ResponseEntity<Cart> payCart(String appUserId, String cartId) {
        if (appUserRepository.findById(appUserId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            AppUser appUser = appUserRepository.findById(appUserId).get();
//            if (cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have any items in cart!");
//            } else if (cart.getIsPaid() == false) {
//                cart.setUpdatedAt(LocalDateTime.now());
//                cart.setPaidAt(LocalDateTime.now());
//                cart.setIsPaid(true);
//                Cart newCart = new Cart(
//                        appUser,
//                        0.0,
//                        LocalDateTime.now()
//                );
//                cartRepository.save(newCart);
//                return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
//            } else {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You paid the cart!");
//            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user found!");
        }
    }
}
