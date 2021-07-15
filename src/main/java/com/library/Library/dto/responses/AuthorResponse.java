package com.library.Library.dto.responses;

import com.library.Library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class AuthorResponse {
    private String id;
    private String fullName;
    private Set<Book> books;
}
