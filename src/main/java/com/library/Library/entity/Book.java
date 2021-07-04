package com.library.Library.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false, updatable = false)
    private String bookGenre;
    @Column(nullable = false, updatable = false)
    private String author;
    private String title;
    private String releaseYear;
    private String description;
    private Double score;
    private Double price;
    private String thumbnail;

    public Book(
            String bookGenre,
            String author,
            String title,
            String releaseYear,
            String description,
            Double score,
            Double price,
            String thumbnail) {
        this.bookGenre = bookGenre;
        this.author = author;
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.score = score;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString(){
        return "Book{" +
                "id = " + id + '\'' +
                ", title = " + title + '\'' +
                ", release year = " + releaseYear + '\'' +
                ", score = " + score + '\'' +
                ", price = " + price + '\'' +
                ", description = " + description + '\'' +
                "}";
    }
}
