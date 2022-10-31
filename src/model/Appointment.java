package model;

import java.util.Date;

public class Appointment {
    private final int id;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private Date start;
    private Date end;
    private int custId;
    private final int userId;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
}
