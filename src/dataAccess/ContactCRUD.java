package dataAccess;

import db.DBConnection;
import model.Contact;
import util.FXUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for performing CRUD operations on Contacts stored in the MySQL database.
 *
 * @author Jeramiah Coffey
 */
public abstract class ContactCRUD {
    /**
     * Get all Contacts.
     *
     * @return A List of all Contacts.
     */
    public static List<Contact> getAll() {
        ArrayList<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                contacts.add(new Contact(id, name, email));
            }
        } catch (SQLException ignored) {
            FXUtils.getInstance().errorAndExit();
            return null;
        }
        return contacts;
    }

    /**
     * Get Contact by id.
     *
     * @return The Contact associated with the id passed in.
     */
    public static Contact get(int id) {
        String query = "SELECT * FROM contacts WHERE Contact_ID=" + id;
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            return new Contact(id, name, email);
        } catch (SQLException ignored) {
            FXUtils.getInstance().errorAndExit();
            return null;
        }
    }
}
