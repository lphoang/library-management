package com.library.Library.dto.requests;

import com.library.Library.constant.PaymentType;
import com.library.Library.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private String orderId;
    private String deliveryAddress;
    private PaymentType paymentType;
}
