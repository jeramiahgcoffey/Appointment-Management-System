package model;

public record Country(int id, String name) {
    @Override
    public String toString() {
        return name;
    }
}
