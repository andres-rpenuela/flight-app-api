package com.tokioschool.flightapp.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Embeddable
public class AirportRawId {
    private long jobId;
    private String acronym;
}
