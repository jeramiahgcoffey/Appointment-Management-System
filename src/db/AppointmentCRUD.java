package db;

import model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AppointmentCRUD {
    /**
     * Get Appointment by id.
     *
     * @param id Unique id associated with the Appointment
     * @return The Appointment associated with the id param
     */
    public static Appointment get(int id) {
        return null;
    }

    /**
     * Get Appointment by customer id.
     *
     * @param custId Unique customer id associated with the Appointment
     * @return The Appointment associated with the id param
     */
    public static List<Appointment> getByCustomerId(int custId) {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
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
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createdAt = rs.getTimestamp("Create_Date");
                Timestamp updatedAt = rs.getTimestamp("Last_Update");
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
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Get all Appointments.
     *
     * @return A List of all Appointments
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
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createdAt = rs.getTimestamp("Create_Date");
                Timestamp updatedAt = rs.getTimestamp("Last_Update");
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
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Persist new Appointment.
     *
     * @param appointment The Appointment to save
     */
    public static void save(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments" +
                " (Title, Description, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        DBConnection.setPreparedStatement(sql);
        PreparedStatement ps = DBConnection.preparedStatement;

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
//        ps.setTimestamp(5, appointment.getStart());
//        ps.setTimestamp(6, appointment.getEnd());
        ps.setTimestamp(7, appointment.getCreatedAt());
        ps.setString(8, appointment.getCreatedBy());
        ps.setTimestamp(9, appointment.getUpdatedAt());
        ps.setString(10, appointment.getUpdatedBy());
        ps.setInt(11, 1);
        ps.setInt(12, 1);
        ps.setInt(13, 1);

        ps.execute();
    }

    /**
     * Persist changes to an Appointment's data.
     *
     * @param appointment The Appointment to change
     * @param params      Values to be changed
     */
    public static void update(Appointment appointment, String[] params) {

    }

    /**
     * Delete the Appointment.
     *
     * @param appointment The Appointment to delete
     */
    public static void delete(Appointment appointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        DBConnection.setPreparedStatement(sql);
        PreparedStatement ps = DBConnection.preparedStatement;
        ps.setInt(1, appointment.getId());

        ps.execute();
    }
}
