package db;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class UserCRUD {
    /**
     * Get User by id.
     *
     * @param username Unique username associated with the User
     * @return The User associated with the id param
     */
    public static User getByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE User_Name = '" + username + "'";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int id = rs.getInt("User_ID");
            String password = rs.getString("password");
            return new User(id, username, password);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Get User by id.
     *
     * @param id Unique id associated with the User
     * @return The User associated with the id param
     */
    public static User get(int id) {
        String query = "SELECT * FROM users WHERE User_ID = '" + id + "'";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String username = rs.getString("User_Name");
            String password = rs.getString("password");
            return new User(id, username, password);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Get all Users.
     *
     * @return A List of all Users
     */
    public static List<User> getAll() {
        return null;
    }

    /**
     * Persist new User.
     *
     * @param user The User to save
     */
    public static void save(User user) {

    }

    /**
     * Persist changes to the User's data.
     *
     * @param user   The User to change
     * @param params Values to be changed
     */
    public static void update(User user, String[] params) {

    }

    /**
     * Delete the object.
     *
     * @param user The object to delete
     */
    public static void delete(User user) {

    }

}
