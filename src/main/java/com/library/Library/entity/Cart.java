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
    @JoinColumn(name = "app_user_id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private AppUser appUser;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Cart(Book book, AppUser appUser, LocalDateTime createdAt) {
        this.book = book;
        this.appUser = appUser;
        this.createdAt = createdAt;
    }
}
