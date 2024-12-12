package com.tokioschool.bookapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Data
@Jacksonized
@AllArgsConstructor @NoArgsConstructor
public class BookDTO {

    int id;
    String title;
    String genre;
    List<Integer> authorsId;
}
