package model;

import db.DivisionCRUD;
import util.TimestampValue;

import java.util.HashMap;
import java.util.List;

/**
 * Class for modeling Customer objects.
 *
 * @author Jeramiah Coffey
 */
public class Customer {
    private final int id;
    private String name;
    private final String address;
    private final String postal;
    private final String phone;
    private final TimestampValue createdAt;
    private final TimestampValue updatedAt;
    private final int divisionId;

    private static final HashMap<Integer, String> divisionMap = new HashMap<Integer, String>();

    public Customer(
            int id,
            String name,
            String address,
            String postal,
            String phone,
            TimestampValue createdAt,
            TimestampValue updatedAt,
            int divisionId
    ) {
        if (divisionMap.isEmpty()) {
            List<Division> allDivisions = DivisionCRUD.getAll();
            assert allDivisions != null;
            for (Division division : allDivisions) {
                divisionMap.put(division.id(), division.name());
            }
        }

        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.divisionId = divisionId;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostal() {
        return postal;
    }

    public String getPhone() {
        return phone;
    }

    public TimestampValue getCreatedAtTimestamp() {
        return createdAt;
    }

    public TimestampValue getUpdatedAtTimestamp() {
        return updatedAt;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
