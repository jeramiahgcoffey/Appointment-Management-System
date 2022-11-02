package db;

import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class CustomerCRUD {
    /**
     * Get Customer by id.
     *
     * @param id Unique id associated with the Customer
     * @return The Customer associated with the id param
     */
    public static Customer get(int id) {
        return null;
    }

    /**
     * Get all Customers.
     *
     * @return A List of all Customers
     */
    public static List<Customer> getAll() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        String query = "SELECT * FROM customers";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Date createdAt = rs.getDate("Create_Date");
                Date updatedAt = rs.getDate("Last_Update");
                String createdBy = rs.getString("Created_By");
                String updatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                customers.add(new Customer(
                        id,
                        name,
                        address,
                        postal,
                        phone,
                        createdAt,
                        updatedAt,
                        createdBy,
                        updatedBy,
                        divisionId
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return customers;
    }

    /**
     * Persist new Customer.
     *
     * @param customer The Customer to save
     */
    public static void save(Customer customer) {

    }

    /**
     * Persist changes to the Customer's data.
     *
     * @param customer The Customer to change
     * @param params   Values to be changed
     */
    public static void update(Customer customer, String[] params) {

    }

    /**
     * Delete the Customer.
     *
     * @param customer The Customer to delete
     */
    public static void delete(Customer customer) {

    }
}
