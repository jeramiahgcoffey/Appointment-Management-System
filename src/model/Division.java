package model;

/**
 * Record class for modeling First Level Divisions.
 *
 * @author Jeramiah Coffey
 */
public record Division(int id, int countryId, String name) {
    @Override
    public String toString() {
        return name;
    }
}
