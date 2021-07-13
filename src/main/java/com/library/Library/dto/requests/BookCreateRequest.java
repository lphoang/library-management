package com.library.Library.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookCreateRequest {
    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Author name is required")
    private String author;
    @NotNull(message = "Book genre is required")
    private String bookGenre;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Score is required")
    private Double score;
    @NotNull(message = "Price is required")
    private Double price;
    @NotNull(message = "Release date is required")
    private String releaseDate;
    @NotNull(message = "Thumbnail is required")
    private String thumbnail;
}
