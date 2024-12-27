package com.tokioschool.flightapp.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString // no recomendable en produccion
@Document(value = "menus")
public class Menu {
    @Id private String id; // Son de tipo String (ObjectId)

    private Instant created;

    private String title;

    private boolean vegetarian;

    private List<Main> mains;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal calories;

    @DBRef
    private Beer beer;
}
