package db;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}