package com.example.amazinghelpdesk.model;

import java.util.Date;

public class Reservation {
    private String staffId;
    private String id;
    private Date date;
    private String customerName;

    public Reservation(String staffId, String id, Date date, String customerName) {
        this.staffId = staffId;
        this.id = id;
        this.date = date;
        this.customerName = customerName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
