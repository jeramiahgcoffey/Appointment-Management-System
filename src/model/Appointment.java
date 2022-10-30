package model;

import java.util.Date;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private Date start;
    private Date end;
    private int custId;
    private int userId;

    public Appointment(
            int id,
            String title,
            String description,
            String location,
            int contactId,
            String type,
            Date start,
            Date end,
            int custId,
            int userId
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custId = custId;
        this.userId = userId;
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

    public int getContactId() {
        return contactId;
    }

    public String getType() {
        return type;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getCustId() {
        return custId;
    }

    public int getUserId() {
        return userId;
    }
}
