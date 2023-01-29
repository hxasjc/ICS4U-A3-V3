package hxasjc.ics4ua3v3;

import hxasjc.ics4ua3v3.monsters.Monster;
import javafx.scene.control.Button;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault());

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

    public static String stackTraceToString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
