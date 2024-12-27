package com.tokioschool.flightapp.listeners.ids;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter @Setter
public abstract class UUIDDocument {
    @Id private UUID id;
}