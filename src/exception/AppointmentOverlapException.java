package exception;

/**
 * Exception to represent the attempt to schedule an appointment that overlaps with an existing appointment.
 *
 * @author Jeramiah Coffey
 */
public class AppointmentOverlapException extends Exception {

    /**
     * Constructs an AppointmentOverlap exception when thrown.
     */
    public AppointmentOverlapException() {
        super("The appointment time selected collides with an existing appointment for this customer.");
    }
}
