package com.library.Library.controller.user;

import com.library.Library.dto.requests.AddItemRequest;
import com.library.Library.dto.requests.RemoveItemRequest;
import com.library.Library.entity.Cart;
import com.library.Library.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Cart>> getItems(@PathVariable("id") String id){
        return cartService.getCartsByUser(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addItem(@RequestBody AddItemRequest request){
        return cartService.addItem(request);
    }

    @PostMapping("/remove")
    public void removeItemFromOrder(@RequestBody RemoveItemRequest request){
        cartService.removeItem(request);
    }

    @PostMapping(value = "/pay/{id}", params = "c-id")
    public ResponseEntity<Cart> payCart(@PathVariable("id") String id, @RequestParam("c-id") String cartId){
        return cartService.payCart(id, cartId);
    }
}
