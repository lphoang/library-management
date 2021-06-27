package com.library.Library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, updatable = false)
    private String bookGenreId;
    @Column(nullable = false, updatable = false)
    private String authorId;
    private String name;
    private String releaseYear;
    private String description;
    private Double score;
    private Double price;
    private String thumbnail;
    private String bookId;

    public Book(String name, String releaseYear, String description, Double score, Double price, String thumbnail) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.score = score;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public Book() {

    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    @Override
    public String toString(){
        return "Book{" +
                "id = " + id + '\'' +
                ", name = " + name + '\'' +
                ", release year = " + releaseYear + '\'' +
                ", score = " + score + '\'' +
                ", price = " + price + '\'' +
                ", description = " + description + '\'' +
                "}";
    }
}
