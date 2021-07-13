package com.library.Library.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    private String id;
    private String fullName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Book> bookSet = new HashSet<>();

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
