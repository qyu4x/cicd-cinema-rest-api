package com.coffekyun.cinema.util;

import java.time.*;
import java.util.Date;

public class TimeConversion {
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalTime convertToLocalTimeViaInstant(Date dateToConvert) {
        return  Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
    }
    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return  Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
