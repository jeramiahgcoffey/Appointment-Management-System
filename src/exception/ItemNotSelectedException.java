package exception;

/**
 * Exception to represent an operation being performed on an item which has not been selected.
 */
public class ItemNotSelectedException extends Exception {

    /**
     * Constructs an ItemNotSelectedException when thrown.
     */
    public ItemNotSelectedException(String message) {
        super(message);
    }
}
