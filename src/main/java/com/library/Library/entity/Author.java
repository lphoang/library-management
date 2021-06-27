package com.library.Library.entity;


import javax.persistence.*;

@Entity
public class Author {
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_sequence"
    )
    private Long id;
    private String fullName;

    public Author(String fullName) { this.fullName = fullName; }

    public Author() { }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getFullName() { return fullName; }
}
