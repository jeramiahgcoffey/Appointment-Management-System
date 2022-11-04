package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TimestampValue(Timestamp originalValue) {

    public LocalDateTime toLocalDateTime() {
        if (originalValue == null) return null;
        return originalValue.toLocalDateTime();
    }

    public String displayFormat() {
        if (originalValue == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return originalValue.toLocalDateTime().format(formatter);
    }
}
