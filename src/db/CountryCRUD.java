package db;

import model.Country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class CountryCRUD {
    /**
     * Get all Countries.
     *
     * @return A List of all Countries
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
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return countries;
    }

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
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}

