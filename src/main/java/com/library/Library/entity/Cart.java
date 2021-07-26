package com.library.Library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    @JoinColumn(name = "order_detail_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private OrderDetail orderDetail;
    private Integer quantity;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private Double total;

    public Cart(Book book, Integer quantity, OrderDetail orderDetail, LocalDateTime createdAt, Double total) {
        this.book = book;
        this.quantity = quantity;
        this.orderDetail = orderDetail;
        this.createdAt = createdAt;
        this.total = total;
    }
}
