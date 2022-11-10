package exception;

/**
 * Exception to represent the attempt to schedule an appointment outside of business hours.
 *
 * @author Jeramiah Coffey
 */
public class OutsideBusinessHoursException extends Exception {

    /**
     * Constructs an AppointmentOverlap exception when thrown.
     */
    public OutsideBusinessHoursException() {
        super("Appointments may be scheduled from 8:00 AM to 10:00 PM Eastern Standard Time.");
    }
}
