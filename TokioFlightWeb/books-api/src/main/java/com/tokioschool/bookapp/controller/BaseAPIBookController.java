package com.tokioschool.bookapp.controller;

import com.tokioschool.bookapp.dto.BookDTO;
import com.tokioschool.bookapp.dto.BookRequestDTO;
import com.tokioschool.bookapp.dto.BookSearchRequestDTO;
import com.tokioschool.bookapp.dto.PageDTO;
import com.tokioschool.bookapp.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<BookDTO> getBookById(@PathVariable int bookId) {
        BookDTO bookDTO = bookService.getBookById(bookId);

        return ResponseEntity.ok(bookDTO);
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createBook(
            @Valid @RequestBody BookRequestDTO bookRequestDTO ) {
        BookDTO bookDTO = bookService.createBook(bookRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }

    @PutMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> editBook(
            @Valid @Positive @Min(1) @PathVariable int bookId,
            @Valid @RequestBody BookRequestDTO bookRequestDTO ) {
        BookDTO bookDTO = bookService.editBook(bookId,bookRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PatchMapping("/books/genre/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> pathBookGenreBook(
            @Valid @Positive @Min(1) @PathVariable int bookId,
            @Valid @RequestBody BookRequestDTO bookRequestDTO
    ){// moficia el atributo de un
        BookDTO bookDTO = bookService.editBookGenre(bookId,bookRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @DeleteMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteBookById(
            @Valid @Positive @Min(1) @PathVariable int bookId
    ){// moficia el atributo de un
        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }
}
