package dataAccess;

import db.DBConnection;
import model.Country;
import util.FXUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for performing CRUD operations on Countries stored in the MySQL database.
 *
 * @author Jeramiah Coffey
 */
public abstract class CountryCRUD {
    /**
     * Get all Countries.
     *
     * @return A List of all Countries.
     */
    public static List<Country> getAll() {
        ArrayList<Country> countries = new ArrayList<Country>();
        String query = "SELECT * FROM countries";
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                countries.add(new Country(id, name));
            }
        } catch (SQLException ignored) {
            FXUtils.getInstance().errorAndExit();
            return null;
        }
        return countries;
    }

    /**
     * Get the country associated with a Division ID.
     *
     * @param divisionId The ID of the division associated with the state being queried for.
     */
    public static Country getByDivision(int divisionId) {
        String query = "SELECT first_level_divisions.Division_ID, countries.Country, countries.Country_ID\n" +
                "FROM first_level_divisions\n" +
                "LEFT JOIN countries\n" +
                "ON first_level_divisions.Country_ID = countries.Country_ID\n" +
                "WHERE Division_ID =" + divisionId;
        Connection conn = DBConnection.connection;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int id = rs.getInt("Country_ID");
            String name = rs.getString("Country");
            return new Country(id, name);
        } catch (SQLException ignored) {
            FXUtils.getInstance().errorAndExit();
            return null;
        }
    }
}

