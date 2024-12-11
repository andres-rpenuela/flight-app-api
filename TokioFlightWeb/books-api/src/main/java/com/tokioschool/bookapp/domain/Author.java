package com.tokioschool.bookapp.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Author {
    int id;
    String name;
}
