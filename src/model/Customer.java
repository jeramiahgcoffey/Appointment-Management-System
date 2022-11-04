package model;

import db.DivisionCRUD;
import util.DateTimeFormat;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

public class Customer {
    private final int id;
    private String name;
    private String address;
    private String postal;
    private String state;
    private String phone;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;
    private final String createdBy;
    private final String updatedBy;
    private int divisionId;

    private static final HashMap<Integer, String> divisionMap = new HashMap<Integer, String>();

    public Customer(
            int id,
            String name,
            String address,
            String postal,
            String phone,
            Timestamp createdAt,
            Timestamp updatedAt,
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
        this.state = divisionMap.get(divisionId);
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.divisionId = divisionId;
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

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public ZonedDateTime getCreatedAt() {
        return DateTimeFormat.sqlToLocal(createdAt);
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
