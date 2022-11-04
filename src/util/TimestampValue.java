package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TimestampValue(Timestamp originalValue) {
    public static TimestampValue now() {
        return new TimestampValue(new Timestamp(System.currentTimeMillis()));
    }

    public LocalDateTime toLocalDateTime() {
        if (originalValue == null) return null;
        return originalValue.toLocalDateTime();
    }

    public String displayFormat() {
        if (originalValue == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        return originalValue.toLocalDateTime().format(formatter);
    }
}
