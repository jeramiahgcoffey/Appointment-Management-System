package db;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User> {
    /** Stores single instance of this class. */
    private static UserDAO instance;

    /** Private constructor to ensure single instance. */
    private UserDAO() {}

    /**
     * Gets the single instance of this class
     * @return This class instance
     */
    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * Get User by id.
     *
     * @param username Unique username associated with the User
     * @return The User associated with the id param
     */
    public User getByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE User_Name = '" + username + "'";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int id = rs.getInt("User_ID");
            String password = rs.getString("Password");
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
    @Override
    public Optional<User> get(int id) {
        return Optional.empty();
    }

    /**
     * Get all Users.
     *
     * @return A List of all Users
     */
    @Override
    public List<User> getAll() {
        return null;
    }

    /**
     * Persist changes to the User.
     *
     * @param user The User to save
     */
    @Override
    public void save(User user) {

    }

    /**
     * Make changes to the User's data.
     *
     * @param user   The User to change
     * @param params Values to be changed
     */
    @Override
    public void update(User user, String[] params) {

    }

    /**
     * Delete the User.
     *
     * @param user The User to delete
     */
    @Override
    public void delete(User user) {

    }
}
