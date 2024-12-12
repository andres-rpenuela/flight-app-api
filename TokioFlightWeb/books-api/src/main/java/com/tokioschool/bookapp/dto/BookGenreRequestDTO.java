package com.tokioschool.bookapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Para mapear la request de busqueda por genero
 */
@Value
@Builder
@Jacksonized
public class BookGenreRequestDTO {

    @NotBlank String genre;
}
