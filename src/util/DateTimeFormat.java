package util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DateTimeFormat {
    public static ZonedDateTime sqlToLocal(Timestamp dt) {
        if (dt == null) return null;
        String val = dt.toLocalDateTime() + "Z";
        Instant timestamp = Instant.parse(val);
        return timestamp.atZone(ZoneId.systemDefault());
    }
}
