package com.tokioschool.bookapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    int id;
    String name;
}
