package com.tokioschool.bookapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Para mapear un libro en la fase de creacion
 */
@Value
@Builder
@Jacksonized
public class BookRequestDTO {
    @NotBlank String title;
    @NotBlank String genre;
    @Positive @NotNull Integer authorId;
}
