package com.library.Library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.Library.constant.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutCart {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private LocalDateTime checkedOutAt;
    @JoinColumn(name = "app_user_id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private AppUser appUser;
    @JoinColumn(name = "order_detail_id", nullable = false)
    @OneToOne
    private OrderDetail orderDetail;
    private String deliveryAddress;
    private PaymentType paymentType;

    public CheckoutCart(LocalDateTime checkedOutAt, AppUser appUser, OrderDetail orderDetail, PaymentType paymentType, String deliveryAddress) {
        this.checkedOutAt = checkedOutAt;
        this.appUser = appUser;
        this.orderDetail = orderDetail;
        this.paymentType = paymentType;
        this.deliveryAddress = deliveryAddress;
    }
}
