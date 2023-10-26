package io.jacobking.simpleticket.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");

    private DateUtil() {

    }

    public static String now() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

}
