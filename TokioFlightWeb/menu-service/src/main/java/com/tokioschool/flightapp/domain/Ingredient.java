package com.tokioschool.flightapp.domain;

import lombok.*;

/**
 * Se trata de una clase que estará dentre de Main,
 * y no será una colección o documento que se persite de forma
 * independiente
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString // no recomendable en produccion
public class Ingredient {

    private String name;
}
