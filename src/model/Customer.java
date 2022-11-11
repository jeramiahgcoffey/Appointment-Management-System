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
    private final int divisionId;

    private static final HashMap<Integer, String> divisionMap = new HashMap<>();

    /**
     * Instantiates Customer objects. Fetches and stores static divisionMap if it is currently empty.
     *
     * @param id         The customer id.
     * @param name       The customer name.
     * @param address    The customer address.
     * @param postal     The customer postal code.
     * @param phone      The customer phone number.
     * @param createdAt  The customer creation DateTimeValue.
     * @param updatedAt  The customer modification DateTimeValue.
     * @param divisionId The customer first level division id.
     */
    public Customer(
            int id,
            String name,
            String address,
            String postal,
            String phone,
            DateTimeValue createdAt,
            DateTimeValue updatedAt,
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

    /**
     * Get the Customer ID.
     *
     * @return The Customer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the Customer name.
     *
     * @return The Customer name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Customer address.
     *
     * @return The Customer address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the Customer postal code.
     *
     * @return The Customer postal code.
     */
    public String getPostal() {
        return postal;
    }

    /**
     * Get the Customer phone number.
     *
     * @return The Customer phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Get the Customer creation DateTimeValue object.
     *
     * @return The Customer creation DateTimeValue object.
     */
    public DateTimeValue getCreatedAtTimestamp() {
        return createdAt;
    }

    /**
     * Get the Customer modification DateTimeValue object.
     *
     * @return The Customer modification DateTimeValue object.
     */
    public DateTimeValue getUpdatedAtTimestamp() {
        return updatedAt;
    }

    /**
     * Get the Customer first level division id.
     *
     * @return The Customer first level division id.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Get the Customer creation display format.
     *
     * @return The Customer creation display format.
     */
    public String getCreatedAt() {
        return createdAt.displayFormat();
    }

    /**
     * Get the Customer modification display format.
     *
     * @return The Customer modification display format.
     */
    public String getUpdatedAt() {
        return updatedAt.displayFormat();
    }

    /**
     * Get the Customer division name based on the divisionId.
     *
     * @return The Customer division name based on the divisionId.
     */
    public String getState() {
        return Objects.requireNonNull(DivisionCRUD.get(divisionId)).name();
    }
}
