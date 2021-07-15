package com.library.Library.dto.responses;

import com.library.Library.entity.Book;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookGenreResponse {
    private String id;
    private String title;
    private Set<Book> books;
}
