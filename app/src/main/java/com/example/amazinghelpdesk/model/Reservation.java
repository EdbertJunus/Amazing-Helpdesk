package com.example.amazinghelpdesk.model;

import java.sql.Time;
import java.util.Date;

public class Reservation {
    private String staffId;
    private String reservationId;
    private Date date;
    private Time time;
    private String customerName;

    public Reservation(String staffId, String id, Date date, Time time, String customerName) {
        this.staffId = staffId;
        this.reservationId = id;
        this.date = date;
        this.time = time;
        this.customerName = customerName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String id) {
        this.reservationId = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
