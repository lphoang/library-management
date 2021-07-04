package com.library.Library.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    private String id;
    private String fullName;

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
