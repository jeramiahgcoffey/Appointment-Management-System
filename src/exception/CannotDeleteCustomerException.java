package exception;

/**
 * Exception to represent the attempt to delete a customer with existing appointments.
 *
 * @author Jeramiah Coffey
 */
public class CannotDeleteCustomerException extends Exception {

    /**
     * Constructs a CannotDeleteCustomer exception when thrown.
     */
    public CannotDeleteCustomerException() {
        super("Please delete this customer's appointments before deleting the customer record.");
    }
}
