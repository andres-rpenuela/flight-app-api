package com.tokioschool.flightapp.csv;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AirportCsvMapper implements FieldSetMapper<AirportCsv> {

    // cabecera del csv a leer
    public static final String[] AIRPORT_CSV_FIELDS = {
            "id",
            "iden",
            "type",
            "name",
            "latitude_deg",
            "longitude_deg",
            "elevation_ft",
            "continent",
            "iso_country",
            "iso_region",
            "municipality",
            "scheduled_service",
            "gps_code",
            "iata_code",
            "local_code",
            "home_link",
            "wikipediaLink",
            "keywords"
    };

    @Override
    public AirportCsv mapFieldSet(FieldSet fieldSet) throws BindException {
        // mapeo los datos
        return AirportCsv.builder()
                .id(readLong(fieldSet.readString("id")))
                .ident(readString(fieldSet.readString("ident")))
                .type(readAirportType(fieldSet.readString("type")))
                .name(readString(fieldSet.readString("name")))
                .latitudeDeg(readBigDecimal(fieldSet.readString("latitude_deg")))
                .longitudeDeg(readBigDecimal(fieldSet.readString("longitude_deg")))
                .elevationFt(readInt(fieldSet.readString("elevation_ft")))
                .continent(readString(fieldSet.readString("continent")))
                .isoCountry(readString(fieldSet.readString("iso_country")))
                .isoRegion(readString(fieldSet.readString("iso_region")))
                .municipality(readString(fieldSet.readString("municipality")))
                .scheduledService(readString(fieldSet.readString("scheduled_service")))
                .gpsCode(readString(fieldSet.readString("gps_code")))
                .iataCode(readString(fieldSet.readString("iata_code")))
                .localCode(readString(fieldSet.readString("local_code")))
                .homeLink(readString(fieldSet.readString("home_link")))
                .wikipediaLink(readString(fieldSet.readString("wikipediaLink")))
                .keywords(readListOfString(fieldSet.readString("keywords")))
                .build();
    }

    protected Integer readInt(String s){
        return trimToNull(s).map(Integer::parseInt).orElseGet(() -> null);
    }

    protected Long readLong(String s){
        return trimToNull(s).map(Long::parseLong).orElseGet(() -> null);
    }

    protected String readString(String s){
        return StringUtils.trimToNull(s);
    }

    protected AirportCsv.AirportType readAirportType(String s){
        return trimToNull(s).map(AirportCsv.AirportType::ofLabel)
                .orElseGet(() -> null);
    }

    protected BigDecimal readBigDecimal(String s){
        return trimToNull(s).map(BigDecimal::new).orElseGet(() -> null);
    }

    protected List<String> readListOfString(String s){
        return trimToNull(s)
                .map(commaSeparatedValues -> commaSeparatedValues.split(","))
                .map(Arrays::asList)
                .orElseGet(List::of);
    }
    protected Optional<String> trimToNull(String s){
        return Optional.ofNullable(s)
                .map(StringUtils::trimToNull);
    }
}
