package io.jacobking.simpleticket.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");
    public static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private DateUtil() {

    }

    public static String nowLocalDate() {
        return LocalDate.now().format(LOCAL_DATE_FORMAT);
    }

    public static String nowLocalDateTime() {
        return LocalDateTime.now().format(LOCAL_DATE_TIME_FORMAT);
    }

    public static LocalDateTime parseLocalDateTime(final String date) {
        return LocalDateTime.parse(date, LOCAL_DATE_TIME_FORMAT);
    }

    public static LocalDate parseLocalDate(final String date) {
        return LocalDate.parse(date, LOCAL_DATE_FORMAT);
    }
}
