package model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class for modeling User objects.
 *
 * @author Jeramiah Coffey
 */
public class User {
    private final int id;
    private final String username;
    private final String password;

    /**
     * Instantiates user objects
     *
     * @param id       User's unique id
     * @param username User's unique username
     * @param password Plain-text password
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Get the user_id
     *
     * @return The user's id
     */
    public int getUserId() {
        return id;
    }

    /**
     * Get the username
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password
     *
     * @return The plain-text password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public static Map<Integer, User> toMap(List<User> list) {
        return list.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
    }
}
