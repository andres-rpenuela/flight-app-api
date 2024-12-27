package com.tokioschool.flightapp.projection;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@ToString
public class BeerStyleCountAggregate {

    private String style;
    private Long count;
}
