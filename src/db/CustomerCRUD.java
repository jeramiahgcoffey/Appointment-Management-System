package db;

import model.Customer;
import util.TimestampValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for performing CRUD operations on Customers stored in the MySQL database.
 *
 * @author Jeramiah Coffey
 */
public abstract class CustomerCRUD {
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
                TimestampValue createdAt = new TimestampValue(rs.getTimestamp("Create_Date"));
                TimestampValue updatedAt = new TimestampValue(rs.getTimestamp("Last_Update"));
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
     * @param customer The Customer to save.
     */
    public static void save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (" +
                "Customer_ID, Customer_Name, Address, Postal_Code," +
                " Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?)";

        DBConnection.setPreparedStatement(sql);
        PreparedStatement ps = DBConnection.preparedStatement;

        ps.setInt(1, customer.getId());
        ps.setString(2, customer.getName());
        ps.setString(3, customer.getAddress());
        ps.setString(4, customer.getPostal());
        ps.setString(5, customer.getPhone());
        ps.setTimestamp(6, customer.getCreatedAtTimestamp().originalValue());
        ps.setString(7, null);
        ps.setTimestamp(8, customer.getUpdatedAtTimestamp().originalValue());
        ps.setString(9, null);
        ps.setInt(10, customer.getDivisionId());

        ps.execute();
    }

    /**
     * Persist changes to the Customer's data.
     *
     * @param customer The Customer to change.
     */
    public static void update(Customer customer) throws SQLException {
        String sql = "UPDATE customers " +
                "SET Customer_Name = ?," +
                "Address = ?," +
                "Postal_Code = ?," +
                "Phone = ?," +
                "Last_Update = ?," +
                "Division_ID = ? " +
                "WHERE Customer_ID = ?";

        DBConnection.setPreparedStatement(sql);
        PreparedStatement ps = DBConnection.preparedStatement;

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostal());
        ps.setString(4, customer.getPhone());
        ps.setTimestamp(5, customer.getUpdatedAtTimestamp().originalValue());
        ps.setInt(6, customer.getDivisionId());
        ps.setInt(7, customer.getId());

        ps.execute();
    }

    /**
     * Delete the Customer.
     *
     * @param customer The Customer to delete.
     */
    public static void delete(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        DBConnection.setPreparedStatement(sql);
        PreparedStatement ps = DBConnection.preparedStatement;
        ps.setInt(1, customer.getId());

        ps.execute();
    }
}
