package db;

import model.Appointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAO implements DAO<Appointment>{
    /** Stores single instance of this class. */
    private static AppointmentDAO instance;

    /** Private constructor to ensure single instance. */
    private AppointmentDAO() {}

    /**
     * Gets the single instance of this class
     * @return This class instance
     */
    public static AppointmentDAO getInstance() {
        if (instance == null) {
            instance = new AppointmentDAO();
        }
        return instance;
    }

    /**
     * Get Appointment by id.
     *
     * @param id Unique id associated with the Appointment
     * @return The Appointment associated with the id param
     */
    @Override
    public Appointment get(int id) {
        return null;
    }

    /**
     * Get all Appointments.
     *
     * @return A List of all Appointments
     */
    @Override
    public List<Appointment> getAll() {
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
            Date start = rs.getDate("Start");
            Date end = rs.getDate("End");
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
                    custId,
                    userId
            ));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return appointments;
    }

    /**
     * Persist changes to the Appointment.
     *
     * @param appointment The Appointment to save
     */
    @Override
    public void save(Appointment appointment) {

    }

    /**
     * Make changes to the Appointment's data.
     *
     * @param appointment The Appointment to change
     * @param params      Values to be changed
     */
    @Override
    public void update(Appointment appointment, String[] params) {

    }

    /**
     * Delete the Appointment.
     *
     * @param appointment The Appointment to delete
     */
    @Override
    public void delete(Appointment appointment) {

    }
}
