package db;

import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDAO implements DAO<Customer> {
    /**
     * Stores single instance of this class.
     */
    private static CustomerDAO instance;

    /**
     * Private constructor to ensure single instance.
     */
    private CustomerDAO() {
    }

    /**
     * Gets the single instance of this class
     *
     * @return This class instance
     */
    public static CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }

    /**
     * Get Customer by id.
     *
     * @param id Unique id associated with the Customer
     * @return The Customer associated with the id param
     */
    @Override
    public Customer get(int id) {
        return null;
    }

    /**
     * Get all Customers.
     *
     * @return A List of all Customers
     */
    @Override
    public List<Customer> getAll() {
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
    @Override
    public void save(Customer customer) {

    }

    /**
     * Persist changes to the Customer's data.
     *
     * @param customer The Customer to change
     * @param params   Values to be changed
     */
    @Override
    public void update(Customer customer, String[] params) {

    }

    /**
     * Delete the Customer.
     *
     * @param customer The Customer to delete
     */
    @Override
    public void delete(Customer customer) {

    }
}
