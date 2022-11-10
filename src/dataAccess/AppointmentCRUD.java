package dataAccess;

import db.DBConnection;
import model.Appointment;
import model.Customer;
import util.DateTimeValue;
import util.FXUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for performing CRUD operations on Appointments stored in the MySQL database.
 *
 * @author Jeramiah Coffey
 */
public abstract class AppointmentCRUD {
    /**
     * Get Appointments by customer id.
     *
     * @param custId Unique customer id associated with the Appointment.
     * @return A List of the customer's appointments.
     */
    public static List<Appointment> getByCustomerId(int custId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE Customer_ID=" + custId;
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                DateTimeValue start = new DateTimeValue(rs.getTimestamp("Start"));
                DateTimeValue end = new DateTimeValue(rs.getTimestamp("End"));
                DateTimeValue createdAt = new DateTimeValue(rs.getTimestamp("Create_Date"));
                DateTimeValue updatedAt = new DateTimeValue(rs.getTimestamp("Last_Update"));
                String createdBy = rs.getString("Created_By");
                String updatedBy = rs.getString("Last_Updated_By");
                int userId = rs.getInt("User_ID");
                appointments.add(new Appointment(
                        id,
                        title,
                        description,
                        location,
                        contactId,
                        type,
                        start,
                        end,
                        createdAt,
                        updatedAt,
                        createdBy,
                        updatedBy,
                        custId,
                        userId
                ));
            }
            return appointments;
        } catch (SQLException ignored) {
            FXUtils.getInstance().errorAndExit();
            return null;
        }
    }

    /**
     * Get all Appointments.
     *
     * @return A List of all Appointments.
     */
    public static List<Appointment> getAll() {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        String query = "SELECT * FROM appointments";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                DateTimeValue start = new DateTimeValue(rs.getTimestamp("Start"));
                DateTimeValue end = new DateTimeValue(rs.getTimestamp("End"));
                DateTimeValue createdAt = new DateTimeValue(rs.getTimestamp("Create_Date"));
                DateTimeValue updatedAt = new DateTimeValue(rs.getTimestamp("Last_Update"));
                String createdBy = rs.getString("Created_By");
                String updatedBy = rs.getString("Last_Updated_By");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                appointments.add(new Appointment(
                        id,
                        title,
                        description,
                        location,
                        contactId,
                        type,
                        start,
                        end,
                        createdAt,
                        updatedAt,
                        createdBy,
                        updatedBy,
                        custId,
                        userId
                ));
            }
            return appointments;
        } catch (SQLException ignored) {
            FXUtils.getInstance().errorAndExit();
            return null;
        }
    }

    /**
     * Persist new Appointment.
     *
     * @param appointment The Appointment to save.
     */
    public static void save(Appointment appointment) {
        String sql = "INSERT INTO appointments" +
                " (Title, Description, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            DBConnection.setPreparedStatement(sql);

            PreparedStatement ps = DBConnection.preparedStatement;

            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, appointment.getStartTimestamp().originalValue());
            ps.setTimestamp(6, appointment.getEndTimestamp().originalValue());
            ps.setTimestamp(7, appointment.getCreatedAt());
            ps.setString(8, appointment.getCreatedBy());
            ps.setTimestamp(9, appointment.getUpdatedAt());
            ps.setString(10, appointment.getUpdatedBy());
            ps.setInt(11, appointment.getCustId());
            ps.setInt(12, appointment.getUserId());
            ps.setInt(13, appointment.getContact().id());

            ps.execute();
        } catch (SQLException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
    }

    /**
     * Persist changes to an Appointment's data.
     *
     * @param appointment The Appointment to change.
     */
    public static void update(Appointment appointment) {
        String sql = "UPDATE appointments " +
                "SET Title = ?," +
                "Description = ?," +
                "Location = ?," +
                "Type = ?," +
                "Start = ?," +
                "End = ?, " +
                "Last_Update = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        try {
            DBConnection.setPreparedStatement(sql);

            PreparedStatement ps = DBConnection.preparedStatement;

            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, appointment.getStartTimestamp().originalValue());
            ps.setTimestamp(6, appointment.getEndTimestamp().originalValue());
            ps.setTimestamp(7, appointment.getUpdatedAt());
            ps.setInt(8, appointment.getCustId());
            ps.setInt(9, appointment.getUserId());
            ps.setInt(10, appointment.getContact().id());
            ps.setInt(11, appointment.getId());

            ps.execute();
        } catch (SQLException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
    }

    /**
     * Delete an Appointment.
     *
     * @param appointment The Appointment to delete.
     */
    public static void delete(Appointment appointment) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try {
            DBConnection.setPreparedStatement(sql);

            PreparedStatement ps = DBConnection.preparedStatement;
            ps.setInt(1, appointment.getId());
            ps.execute();
        } catch (SQLException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
    }

    /**
     * Get total count of appointments based on the filters passed in.
     * Returns total count of all appointments if three null values are passed in.
     *
     * @param selectedCustomer Nullable Customer object with the ID to query for.
     * @param selectedMonth    Nullable Month object with the value to query for.
     * @param selectedType     Nullable string value of the type to query for.
     */
    public static int getTotals(Month selectedMonth, String selectedType, Customer selectedCustomer) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(Appointment_ID) FROM appointments");
        List<String> conditions = new ArrayList<>();

        if (selectedMonth != null || selectedType != null || selectedCustomer != null) {
            query.append(" WHERE");

            if (selectedMonth != null) {
                int year = LocalDate.now().getYear();
                int month = selectedMonth.getValue();

                LocalDate monthLD = LocalDate.of(year, month, 1);
                Timestamp startOfMonth = Timestamp.valueOf(monthLD.atStartOfDay());
                Timestamp endOfMonth = Timestamp.valueOf(monthLD.plusMonths(1).atStartOfDay());

                conditions.add(" Start BETWEEN '" + startOfMonth + "' AND '" + endOfMonth + "'");
            }

            if (selectedType != null) {
                conditions.add(" Type='" + selectedType + "'");
            }

            if (selectedCustomer != null) {
                conditions.add(" Customer_ID='" + selectedCustomer.getId() + "'");
            }
        }

        for (int i = 0; i < conditions.toArray().length; i++) {
            query.append(conditions.get(i));
            if (i < conditions.toArray().length - 1) {
                query.append(" AND");
            }
        }

        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query.toString());
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            FXUtils.getInstance().error(e.getMessage());
            return 0;
        }
    }
}
