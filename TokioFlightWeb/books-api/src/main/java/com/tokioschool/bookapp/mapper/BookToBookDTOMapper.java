package com.tokioschool.bookapp.mapper;

import com.tokioschool.bookapp.converter.AuthorToAuthorIdConverter;
import com.tokioschool.bookapp.domain.Book;
import com.tokioschool.bookapp.dto.BookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookToBookDTOMapper {

    private final ModelMapper modelMapper;

    public BookToBookDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        init();
    }

    private void init() {
        modelMapper.typeMap(Book.class, BookDTO.class)
                .addMappings(mapping ->
                        mapping.using(new AuthorToAuthorIdConverter()).map(Book::getAuthors, BookDTO::setAuthorsId)
                );

    }
}
