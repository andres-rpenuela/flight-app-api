package com.tokioschool.bookapp.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Book {
    private Integer id;
    private String title;
    private String gender;
    private List<Author> authors;

}
