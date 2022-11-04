package db;

import model.Division;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DivisionCRUD {
    /**
     * Get Divisions by Country ID.
     *
     * @param countryId The ID of the country that the divisions belong to.
     * @return A List of all Division that belong to the country passed in.
     */
    public static List<Division> getAllByCountry(int countryId) {
        ArrayList<Division> divisions = new ArrayList<Division>();
        String query = "SELECT * FROM first_level_divisions WHERE country_id=" + countryId;
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");

                divisions.add(new Division(id, countryId, name));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return divisions;
    }

    /**
     * Get all Divisions.
     *
     * @return A List of all Divisions.
     */
    public static List<Division> getAll() {
        ArrayList<Division> divisions = new ArrayList<Division>();
        String query = "SELECT * FROM first_level_divisions";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                int countryId = rs.getInt("Country_ID");
                String name = rs.getString("Division");

                divisions.add(new Division(id, countryId, name));
            }
            return divisions;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public static Division get(int id) {
        String query = "SELECT * FROM first_level_divisions WHERE Division_ID=" + id;
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int countryId = rs.getInt("Country_ID");
            String name = rs.getString("Division");

            return new Division(id, countryId, name);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}

