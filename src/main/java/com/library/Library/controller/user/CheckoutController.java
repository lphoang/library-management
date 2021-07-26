package com.library.Library.controller.user;

import com.library.Library.dto.requests.CheckoutRequest;
import com.library.Library.entity.CheckoutCart;
import com.library.Library.service.impl.CheckoutCartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/check-out")
@AllArgsConstructor
public class CheckoutController {
    private final CheckoutCartService checkoutCartService;

    @PostMapping
    public ResponseEntity<CheckoutCart> checkOutCart(@RequestBody CheckoutRequest request){
        return checkoutCartService.checkOutCart(request);
    }
}
