package com.tokioschool.bookapp.dto;

import lombok.Builder;
import lombok.Value;

/**
 * para mapear la busqueda de libros paginados
 */
@Value
@Builder
public class BookSearchRequestDTO {
    String genre;
    int page;
    int pageSize;
}
