package com.progmasters.reactblog.utils;

import java.time.*;

public class DateUtils {

    public static final ZoneId DATABASE_SERVER_TIME_ZONE = ZoneId.of("UTC");
    public static final ZoneId LOCAL_TIME_ZONE = ZoneId.of("Europe/Budapest");

    public static ZonedDateTime getCurrentServerTime() {
        return ZonedDateTime.now(DATABASE_SERVER_TIME_ZONE);
    }

    public static ZonedDateTime convertLocalDateTimeToZonedDateTime(LocalDateTime dateTime) {
        return ZonedDateTime.of(dateTime, DATABASE_SERVER_TIME_ZONE);
    }

    public static ZonedDateTime convertLocalDateToZonedDateTime(LocalDate dateTime) {
        return ZonedDateTime.of(dateTime, LocalTime.MIN, DATABASE_SERVER_TIME_ZONE);
    }

    public static LocalDateTime localizeDateTimeFromZonedDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(LOCAL_TIME_ZONE).toLocalDateTime();
    }

    public static LocalDate localizeDateFromZonedDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(LOCAL_TIME_ZONE).toLocalDate();
    }

}

