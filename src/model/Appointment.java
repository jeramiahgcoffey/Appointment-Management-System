package model;

import dataAccess.ContactCRUD;
import util.TimestampValue;

import java.sql.Timestamp;

/**
 * Class for modeling Appointment objects.
 *
 * @author Jeramiah Coffey
 */
public class Appointment {
    private final int id;
    private final String title;
    private final String description;
    private final String location;
    private final String type;
    private final TimestampValue start;
    private final TimestampValue end;
    private final TimestampValue createdAt;
    private final TimestampValue updatedAt;
    private final String createdBy;
    private final String updatedBy;
    private final int custId;
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

    @Override
    public String toString() {
        return "Apt ID " + id + " - " + title + " at " + start.displayFormat();
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

    public String getStart() {
        return start.displayFormat();
    }

    public String getEnd() {
        return end.displayFormat();
    }
}
