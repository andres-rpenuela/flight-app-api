package com.tokioschool.flightapp.domain;

import com.tokioschool.flightapp.listeners.ids.UUIDDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
@Document(collection = "beers",value = "beers")
public class Beer extends UUIDDocument {

    private String name;
    private String style;
}
