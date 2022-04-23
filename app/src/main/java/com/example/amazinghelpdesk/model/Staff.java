package com.example.amazinghelpdesk.model;

import java.sql.Time;

public class Staff {
    private String name;
    private boolean availableStatus;
    private String id;
    private String email;
    private String phone;
    private Time availableStart;
    private Time availableEnd;

    public Staff(String name, boolean availableStatus, String id, String email, String phone, Time availableStart, Time availableEnd) {
        this.name = name;
        this.availableStatus = availableStatus;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.availableStart = availableStart;
        this.availableEnd = availableEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(boolean availableStatus) {
        this.availableStatus = availableStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Time getAvailableStart() {
        return availableStart;
    }

    public void setAvailableStart(Time availableStart) {
        this.availableStart = availableStart;
    }

    public Time getAvailableEnd() {
        return availableEnd;
    }

    public void setAvailableEnd(Time availableEnd) {
        this.availableEnd = availableEnd;
    }
}
