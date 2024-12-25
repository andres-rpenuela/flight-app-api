package com.tokioschool.flightapp.batch.filter;

import com.tokioschool.flightapp.csv.AirportCsv;
import com.tokioschool.flightapp.domain.AirportRaw;
import com.tokioschool.flightapp.domain.AirportRawId;
import lombok.AllArgsConstructor;

import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Filtrado de las lineas leidas, se podría usar una instancia de
 * la claseFunctionItemProcessor<>{...} con la misma logica
 */
@AllArgsConstructor
public class AirportCsvItemFilter implements FilterItem<AirportCsv,AirportRaw> {
    private Long jobId;

    private static final Predicate<Object> isNull = Objects::isNull;

    @Override
    public AirportRaw filter(AirportCsv item) {
        if( isNull.test( item.getIataCode() ) ) {
            return null;
        }

        if( item.getType() != AirportCsv.AirportType.LARGE_AIRPORT
                && item.getType() != AirportCsv.AirportType.MEDIUM_AIRPORT){
            return null;
        }

        boolean isIsoCountryEs = Optional.ofNullable(item.getIsoCountry())
                .map(String::toUpperCase)
                .filter("ES"::equals).isPresent();

        if(  isIsoCountryEs ){
            return null;
        }

        AirportRawId airportRawId = AirportRawId.builder().jobId(jobId).acronym(item.getIataCode()).build();

        return AirportRaw.builder()
                .airportRawId(airportRawId)
                .lat(item.getLatitudeDeg().setScale(8, RoundingMode.HALF_EVEN))
                .lon(item.getLongitudeDeg().setScale(8,RoundingMode.HALF_EVEN))
                .country(item.getIsoCountry())
                .name(item.getName()).build();
    }
}
