package model;

/**
 * Record class for modeling Country objects.
 *
 * @author Jeramiah Coffey
 */
public record Country(int id, String name) {
    @Override
    public String toString() {
        return name;
    }
}
