package model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Contact(int id, String name, String email) {
    @Override
    public String toString() {
        return name;
    }

    public static Map<Integer, Contact> toMap(List<Contact> list) {
        return list.stream().collect(Collectors.toMap(Contact::id, Function.identity()));
    }
}
