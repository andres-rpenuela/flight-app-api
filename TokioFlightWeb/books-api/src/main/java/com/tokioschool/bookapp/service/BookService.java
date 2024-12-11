package com.tokioschool.bookapp.service;

import com.tokioschool.bookapp.core.exception.NotFoundException;
import com.tokioschool.bookapp.dto.*;

public interface BookService {
    AuthorDTO getAuthorById(int id) throws NotFoundException;
    BookDTO getBookById(int id) throws NotFoundException;
    PageDTO<BookDTO> searchBooksByPageIdAndPageSize(BookSearchRequestDTO searchRequestDTO);
    void deleteBookById(int id) throws NotFoundException;
    BookDTO createBook(BookRequestDTO bookRequestDTO) throws NotFoundException;
    BookDTO editBook(int bookId, BookRequestDTO bookRequestDTO) throws NotFoundException;
}
