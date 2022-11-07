package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Record class for modeling and manipulating SQL Timestamps.
 *
 * @author Jeramiah Coffey
 */
public record TimestampValue(Timestamp originalValue) {
    /**
     * Static method for getting the current moment in time as a TimestampValue object.
     *
     * @return The current moment in time as a TimestampValue object.
     */
    public static TimestampValue now() {
        return new TimestampValue(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Returns a LocalDateTime object from the TimestampValue object.
     */
    public LocalDateTime toLocalDateTime() {
        if (originalValue == null) return null;
        return originalValue.toLocalDateTime();
    }

    /**
     * Returns the display format of the TimestampValue object.
     */
    public String displayFormat() {
        if (originalValue == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        return originalValue.toLocalDateTime().format(formatter);
    }

    /**
     * Returns the hour of the day of the TimestampValue object.
     */
    public int getHour() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(originalValue.getTime());
        return cal.get(Calendar.HOUR);
    }

    /**
     * Returns the minute of the hour of the TimestampValue object.
     */
    public int getMinute() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(originalValue.getTime());
        return cal.get(Calendar.MINUTE);
    }
}
