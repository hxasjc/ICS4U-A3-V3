package hxasjc.ics4ua3v3;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {
    /**
     * Formats dates in a shorter format
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault());

    /**
     * Inserts spaces into a class name before a capital letter to make it human-readable
     * @param clazz The class to generate the name for
     * @return A human-readable version of the class name
     */
    public static String getNiceName(Class<?> clazz) {
        StringBuilder builder = new StringBuilder();
        for (char c : clazz.getSimpleName().toCharArray()) {
            if (Character.isUpperCase(c)) {
                builder.append(" ");
            }
            builder.append(c);
        }
        return builder.toString().strip();
    }

    /**
     * Represent the specified throwable's stack trace as a string
     * @param throwable The throwable to represent as a string
     * @return The stack trace, represented as a string
     */
    public static String stackTraceToString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
