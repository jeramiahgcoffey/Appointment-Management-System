package db;

import model.Contact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class ContactCRUD {
    /**
     * Get all Contacts.
     *
     * @return A List of all Contacts
     */
    public static List<Contact> getAll() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
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
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return contacts;
    }
}
