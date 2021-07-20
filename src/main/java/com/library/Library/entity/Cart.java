package com.library.Library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Book> items = new HashSet<>();

    @JoinColumn(name = "app_user_id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private AppUser appUser;
    private Double total;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime paidAt;
    private Boolean isPaid = false;

    public Cart(Set<Book> items, AppUser appUser, Double total, LocalDateTime createdAt) {
        this.items = items;
        this.appUser = appUser;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Cart(AppUser appUser, Double total, LocalDateTime createdAt) {
        this.appUser = appUser;
        this.createdAt = createdAt;
        this.total = total;
    }
}
