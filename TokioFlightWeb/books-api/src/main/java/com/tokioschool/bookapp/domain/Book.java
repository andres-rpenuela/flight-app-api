package com.tokioschool.bookapp.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Book {
    private Integer id;
    private String title;
    private String genre;
    private List<Author> authors;

}
