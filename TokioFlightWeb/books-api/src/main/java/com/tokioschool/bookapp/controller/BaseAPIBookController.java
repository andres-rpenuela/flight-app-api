package com.tokioschool.bookapp.controller;

import com.tokioschool.bookapp.dto.BookDTO;
import com.tokioschool.bookapp.dto.BookSearchRequestDTO;
import com.tokioschool.bookapp.dto.PageDTO;
import com.tokioschool.bookapp.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/base/api")
@Validated // aplique reglas de validadicón
public class BaseAPIBookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<PageDTO<BookDTO>> searchAllBooks(
            @Valid @RequestParam(value = "genre", required = false) String genre,
            @Valid @RequestParam(value = "page", required = false,defaultValue = "0") int pageNumber,
            @Valid @Min(1) @Max(100) @RequestParam(value = "page_size", required = false,defaultValue = "10") int pageSize
    ) {
        PageDTO<BookDTO> pageDTO = bookService.searchBooksByPageIdAndPageSize(BookSearchRequestDTO.builder()
                .genre(genre)
                .page(pageNumber)
                .pageSize(pageSize).build());

        return ResponseEntity.ok(pageDTO);
    }

}
