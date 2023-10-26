package io.jacobking.simpleticket.utility;

public class MiscUtil {

    private MiscUtil() {

    }

    public static int isNumber(final String string) {
        if (string == null || string.isEmpty())
            return -1;

        try {
           return Integer.parseInt(string);
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

}
