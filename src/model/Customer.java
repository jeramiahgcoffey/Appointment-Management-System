package model;

import dataAccess.DivisionCRUD;
import util.DateTimeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Class for modeling Customer objects.
 *
 * @author Jeramiah Coffey
 */
public class Customer {
    private final int id;
    private final String name;
    private final String address;
    private final String postal;
    private final String phone;
    private final DateTimeValue createdAt;
    private final DateTimeValue updatedAt;
    private final String createdBy;
    private final String updatedBy;
    private final int divisionId;

    private static final HashMap<Integer, String> divisionMap = new HashMap<>();

    public Customer(
            int id,
            String name,
            String address,
            String postal,
            String phone,
            DateTimeValue createdAt,
            DateTimeValue updatedAt,
            String createdBy,
            String updatedBy,
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
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
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

    public DateTimeValue getCreatedAtTimestamp() {
        return createdAt;
    }

    public DateTimeValue getUpdatedAtTimestamp() {
        return updatedAt;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getCreatedAt() {
        return createdAt.displayFormat();
    }

    public String getUpdatedAt() {
        return updatedAt.displayFormat();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getState() {
        return Objects.requireNonNull(DivisionCRUD.get(divisionId)).name();
    }
}
