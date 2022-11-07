package exception;

/**
 * Exception to represent an operation being performed on an item which has not been selected.
 *
 * @author Jeramiah Coffey
 */
public class ItemNotSelectedException extends Exception {

    /**
     * Constructs an ItemNotSelectedException when thrown.
     *
     * @param message The error message.
     */
    public ItemNotSelectedException(String message) {
        super(message);
    }
}
