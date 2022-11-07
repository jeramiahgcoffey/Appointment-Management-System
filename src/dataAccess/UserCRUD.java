package dataAccess;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for performing CRUD operations on Users stored in the MySQL database.
 *
 * @author Jeramiah Coffey
 */
public abstract class UserCRUD {
    /**
     * Get User by id.
     *
     * @param username Unique username associated with the User.
     * @return The User associated with the id param.
     */
    public static User getByUsername(String username) {
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
     * @param id Unique id associated with the User.
     * @return The User associated with the id param.
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
     * @return A List of all Users.
     */
    public static List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                users.add(new User(
                        id,
                        name,
                        null
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return users;
    }
}
