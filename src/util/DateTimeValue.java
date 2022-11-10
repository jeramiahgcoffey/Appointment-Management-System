package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;

/**
 * Record class for modeling and manipulating SQL Timestamps, and converting between LocalDateTimes.
 *
 * @author Jeramiah Coffey
 */
public record DateTimeValue(Timestamp originalValue) {
    /**
     * Static method for getting the current moment in time as a TimestampValue object.
     *
     * @return The current moment in time as a TimestampValue object.
     */
    public static DateTimeValue now() {
        return new DateTimeValue(new Timestamp(System.currentTimeMillis()));
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
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Returns the minute of the hour of the TimestampValue object.
     */
    public int getMinute() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(originalValue.getTime());
        return cal.get(Calendar.MINUTE);
    }

    public boolean isValidBusinessHours() {
        ZonedDateTime businessClosed = ZonedDateTime.of(Objects.requireNonNull(this.toLocalDateTime()).toLocalDate(), LocalTime.of(22, 1), ZoneId.of("America/New_York"));
        ZonedDateTime businessOpened = ZonedDateTime.of(Objects.requireNonNull(this.toLocalDateTime()).toLocalDate(), LocalTime.of(8, 1), ZoneId.of("America/New_York"));
        ZonedDateTime curr = ZonedDateTime.of(Objects.requireNonNull(this.toLocalDateTime()), ZoneId.systemDefault());
        return !curr.isAfter(businessOpened) && !curr.isBefore(businessClosed);
    }

    public boolean isAfterOpen() {
        ZonedDateTime businessOpened = ZonedDateTime.of(Objects.requireNonNull(this.toLocalDateTime()).toLocalDate(), LocalTime.of(8, 1), ZoneId.of("America/New_York"));
        ZonedDateTime aptTime = ZonedDateTime.of(Objects.requireNonNull(this.toLocalDateTime()), ZoneId.systemDefault());
        return aptTime.isBefore(businessOpened);
    }
}
