package com.tokioschool.bookapp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class AuthorDTO {
    int id;
    String name;
}
