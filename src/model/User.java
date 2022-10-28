package model;

/**
 * Models a User
 * @author Jeramiah Coffey
 */
public class User {
    private final int user_id;
    private final String username;
    private final String password;

    /**
     * Instantiates user objects
     * @param user_id User's unique id
     * @param username User's unique username
     * @param password  Plain-text password
     */
    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    /** Get the user_id
     * @return The user's id
     */
    public int getUserId() {
        return user_id;
    }

    /** Get the username
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /** Get the password
     * @return The plain-text password
     */
    public String getPassword() {
        return password;
    }
}
