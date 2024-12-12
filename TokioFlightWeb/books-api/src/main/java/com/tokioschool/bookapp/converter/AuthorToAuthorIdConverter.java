package com.tokioschool.bookapp.converter;

import com.tokioschool.bookapp.domain.Author;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;

public class AuthorToAuthorIdConverter implements  Converter<List<Author>, List<Integer>>{

    @Override
    public List<Integer> convert(MappingContext<List<Author>, List<Integer>> context) {
        return context.getSource().stream().map(Author::getId).toList();
    }
}
