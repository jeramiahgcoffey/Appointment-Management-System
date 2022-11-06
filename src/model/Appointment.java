package model;

import db.ContactCRUD;
import util.TimestampValue;

import java.sql.Timestamp;

public class Appointment {
    private final int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private TimestampValue start;
    private TimestampValue end;
    private TimestampValue createdAt;
    private TimestampValue updatedAt;
    private String createdBy;
    private String updatedBy;
    private int custId;
    private final int userId;

    private final Contact contact;

    public Appointment(
            int id,
            String title,
            String description,
            String location,
            int contactId,
            String type,
            TimestampValue start,
            TimestampValue end,
            TimestampValue createdAt,
            TimestampValue updatedAt,
            String createdBy,
            String updatedBy,
            int custId,
            int userId
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.custId = custId;
        this.userId = userId;

        contact = ContactCRUD.get(contactId);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getStart() {
        return start.displayFormat();
    }

    public String getEnd() {
        return end.displayFormat();
    }

    public TimestampValue getStartTimestamp() {
        return start;
    }

    public TimestampValue getEndTimestamp() {
        return end;
    }

    public Timestamp getCreatedAt() {
        return createdAt.originalValue();
    }

    public Timestamp getUpdatedAt() {
        return updatedAt.originalValue();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public int getCustId() {
        return custId;
    }

    public int getUserId() {
        return userId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(TimestampValue start) {
        this.start = start;
    }

    public void setEnd(TimestampValue end) {
        this.end = end;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
}
