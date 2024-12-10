package com.tokioschool.flightapp.base.transaccions.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bases")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //@Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name="counter", nullable = false)
    private int count ;

}

