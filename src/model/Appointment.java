package model;

import dataAccess.ContactCRUD;
import util.DateTimeValue;

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
    private final DateTimeValue start;
    private final DateTimeValue end;
    private final DateTimeValue createdAt;
    private final DateTimeValue updatedAt;
    private final String createdBy;
    private final String updatedBy;
    private final int custId;
    private final int userId;
    private final Contact contact;

    /**
     * Instantiates Appointment objects. Use contactId to fetch the contact for the appointment
     *
     * @param id          The appointment ID.
     * @param title       The appointment title.
     * @param description The appointment description.
     * @param location    The appointment location.
     * @param contactId   The contact associated with the Appointment
     * @param type        The appointment type.
     * @param start       The appointment start DateTimeValue object.
     * @param end         The appointment end DateTimeValue object.
     * @param createdAt   The appointment creation DateTimeValue object.
     * @param updatedAt   The appointment recent modification DateTimeValue object.
     * @param createdBy   The appointment author.
     * @param updatedBy   The appointment modification author
     * @param userId      The user ID associated with the appointment.
     * @param custId      The customer ID associated with the appointment.
     */
    public Appointment(
            int id,
            String title,
            String description,
            String location,
            int contactId,
            String type,
            DateTimeValue start,
            DateTimeValue end,
            DateTimeValue createdAt,
            DateTimeValue updatedAt,
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

    /**
     * Get the Appointment ID.
     *
     * @return The Appointment ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the Appointment title.
     *
     * @return The Appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the Appointment description.
     *
     * @return The Appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the Appointment location.
     *
     * @return The Appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the Appointment type.
     *
     * @return The Appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * Get the Appointment start DateTimeValue object.
     *
     * @return The Appointment start DateTimeValue object.
     */
    public DateTimeValue getStartDateTimeValue() {
        return start;
    }

    /**
     * Get the Appointment end DateTimeValue object.
     *
     * @return The Appointment end DateTimeValue object.
     */
    public DateTimeValue getEndDateTimeValue() {
        return end;
    }

    /**
     * Get the Appointment createdAt Timestamp object.
     *
     * @return The Appointment createdAt Timestamp object.
     */
    public Timestamp getCreatedAt() {
        return createdAt.originalValue();
    }

    /**
     * Get the Appointment updatedAt Timestamp object.
     *
     * @return The Appointment updatedAt Timestamp object.
     */
    public Timestamp getUpdatedAt() {
        return updatedAt.originalValue();
    }

    /**
     * Get the Appointment author.
     *
     * @return The Appointment author.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Get the Appointment modification author.
     *
     * @return The Appointment modification author.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Get the Appointment customer ID.
     *
     * @return The Appointment customer ID.
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Get the Appointment user ID.
     *
     * @return The Appointment user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Get the Appointment Contact object.
     *
     * @return The Appointment Contact object.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Get the Appointment start display value.
     *
     * @return The Appointment start display value.
     */
    public String getStart() {
        return start.displayFormat();
    }

    /**
     * Get the Appointment end display value.
     *
     * @return The Appointment end display value.
     */
    public String getEnd() {
        return end.displayFormat();
    }
}
