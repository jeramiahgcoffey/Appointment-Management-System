package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DateTimeFormat {
    public static LocalDateTime sqlToLocal(Timestamp dt) {
        if (dt == null) return null;
        return dt.toLocalDateTime();
    }

    public static String displayFormat(LocalDateTime dt) {
        if (dt == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return dt.format(formatter);
    }
}
