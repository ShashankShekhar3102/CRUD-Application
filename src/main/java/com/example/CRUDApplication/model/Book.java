package com.example.CRUDApplication.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Books")
@NoArgsConstructor
@AllArgsConstructor
//@Setter
//@Getter
@ToString
public class Book {

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getAuthor() {
        return author;
    }

    @Column
    private String title;

    @Column
    private String author;

}