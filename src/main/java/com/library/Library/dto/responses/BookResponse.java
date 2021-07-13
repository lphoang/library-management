package com.library.Library.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookResponse {
    private String title;
    private String author;
    private String bookGenre;
    private String description;
    private Double score;
    private Double price;
    private String releaseDate;
    private String thumbnail;
}
