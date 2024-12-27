package com.tokioschool.flightapp.domain;

import lombok.*;

import java.util.List;

/**
 * Se trata de una clase que estará dentre de Menu,
 * y no será una colección o documento que se persite de forma
 * independiente
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString // no recomendable en produccion
public class Main {

    private String name;
    private List<Ingredient> ingredients;
}
