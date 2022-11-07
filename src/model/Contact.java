package model;

/**
 * Record class for modeling Contacts.
 *
 * @author Jeramiah Coffey
 */
public record Contact(int id, String name, String email) {
    @Override
    public String toString() {
        return name;
    }
}
