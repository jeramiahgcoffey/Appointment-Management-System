package model;

public record Division(int id, int countryId, String name) {
    @Override
    public String toString() {
        return name;
    }
}
