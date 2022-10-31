package model;

import java.util.Date;

public class Customer {
    private final int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private final Date createdAt;
    private final Date updatedAt;
    private final String createdBy;
    private final String updatedBy;
    private int divisionId;

    public Customer(
            int id,
            String name,
            String address,
            String postal,
            String phone,
            Date createdAt,
            Date updatedAt,
            String createdBy,
            String updatedBy,
            int divisionId
    ) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
