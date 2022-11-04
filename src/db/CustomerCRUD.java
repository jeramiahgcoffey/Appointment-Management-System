package db;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
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
                Timestamp createdAt = rs.getTimestamp("Create_Date");
                Timestamp updatedAt = rs.getTimestamp("Last_Update");
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
        ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        ps.setString(7, null);
        ps.setString(8, null);
        ps.setString(9, null);
        ps.setInt(10, customer.getDivisionId());

        ps.execute();
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
    public static void delete(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        DBConnection.setPreparedStatement(sql);
        PreparedStatement ps = DBConnection.preparedStatement;
        ps.setInt(1, customer.getId());

        ps.execute();
    }
}
