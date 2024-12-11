package com.tokioschool.bookapp.service;

import com.github.javafaker.Faker;
import com.tokioschool.bookapp.core.exception.NotFoundException;
import com.tokioschool.bookapp.domain.Author;
import com.tokioschool.bookapp.domain.Book;
import com.tokioschool.bookapp.dto.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private List<Book> books;
    private List<Author> authors;

    private final ModelMapper modelMapper;

    @PostConstruct
    public void postConstruct() {
        final Faker faker = new Faker();

        authors = IntStream.range(1, 11)
                .mapToObj(i-> Author.builder().id(i).name(faker.book().author()).build())
                .toList();

        books = IntStream.range(1, 101)
                .mapToObj(i -> Book.builder().title(faker.book().title())
                        .gender(faker.book().genre())
                        .authors(List.of(authors.get( (int) (Math.random() * 10))))
                        .build())
                .toList();
    }

    public AuthorDTO getAuthorById(int id) {
        return authors.stream().filter(author -> author.getId() == id)
                .findFirst()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .orElseThrow(()->new NotFoundException("Author with id %d not found".formatted(id) ));
    }

    public BookDTO getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id)
                .findFirst()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(()->new NotFoundException("Book with id %d not found.".formatted(id)));
    }

    public PageDTO<BookDTO> searchBooksByPageIdAndPageSize(BookSearchRequestDTO searchRequestDTO) {

        if(searchRequestDTO == null) {
            searchRequestDTO = BookSearchRequestDTO.builder()
                    .page(0)
                    .pageSize(5).build();
        }

        List<Book> filteredBooks =
                Optional.ofNullable(searchRequestDTO)
                        .map(BookSearchRequestDTO::getGenre)
                        .map(StringUtils::stripToNull)
                        .map(StringUtils::lowerCase)
                        .map(genre ->
                                books.stream()
                                        .filter(book -> book.getGender().toLowerCase().contains(genre))
                                        .toList())
                        .orElseGet(()->books);

        int star = searchRequestDTO.getPage() * searchRequestDTO.getPageSize(); // first item of Page<T>

        if(star >= filteredBooks.size()) { // there aren't items to show
            return PageDTO.<BookDTO>builder()
                    .items(List.of())
                    .pageNumber(searchRequestDTO.getPage())
                    .pageSize(searchRequestDTO.getPageSize())
                    .totalPages(filteredBooks.size())
                    .build();
        }

        int end = Math.min(star + searchRequestDTO.getPageSize(),filteredBooks.size());

        List<BookDTO> items = IntStream.range(star, end)
                .mapToObj(filteredBooks::get)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();

        return PageDTO.<BookDTO>builder()
                .items(items)
                .pageNumber(searchRequestDTO.getPage())
                .pageSize(searchRequestDTO.getPageSize())
                .totalPages(filteredBooks.size())
                .build();
    }

    @Override
    public void deleteBookById(int id) throws NotFoundException {
        Optional<Book> maybeBook = books.stream().filter(book -> book.getId() == id).findFirst();
        maybeBook.map(book -> books.remove(book))
                .filter(isDeleted -> isDeleted)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
    }

    private synchronized int nextBookId() {
        return books.stream().map(Book::getId)
                .reduce(Math::max)
                .map(id->id + 1)
                .orElse(1);
    }

    public BookDTO createBook(BookRequestDTO bookRequestDTO) {
        Author author = authors.stream().filter(auth -> auth.getId() == bookRequestDTO.getAuthorId())
                .findFirst()
                .orElseThrow(()->new NotFoundException("Author with id %d not found".formatted(bookRequestDTO.getAuthorId())));

        Book book = Book.builder()
                .id(nextBookId())
                .authors(Collections.singletonList(authors.get(bookRequestDTO.getAuthorId())))
                .title(bookRequestDTO.getTitle())
                .gender(bookRequestDTO.getGenre())
                .build();

        return modelMapper.map(book, BookDTO.class);
    }

    public BookDTO editBook(int bookId, BookRequestDTO bookRequestDTO) {

        Book book = books.stream().filter(book1 -> book1.getId() == bookId)
                .findFirst()
                .orElseThrow(()->new NotFoundException("Book with id %d not found".formatted(bookId)));
        Author author = authors.stream().filter(author1 -> author1.getId() == bookRequestDTO.getAuthorId())
                .findFirst()
                .orElseThrow(()->new NotFoundException("Author with id %d not found".formatted(bookId)));

        // get pox
        int posBook = books.indexOf(book);

        // update
        book.setTitle(bookRequestDTO.getTitle());
        book.setGender(bookRequestDTO.getGenre());
        book.setAuthors(Collections.singletonList(author));

        books.add(posBook, book);

        return modelMapper.map(book, BookDTO.class);


    }
}
