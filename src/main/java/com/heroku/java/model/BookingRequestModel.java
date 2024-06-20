package com.heroku.java.model;

import java.sql.Date;

public class BookingRequestModel {
    private Integer requestId;
    private String customerName;
    private String email;
    private Date travelDate;
    private String packageType;
    private int noOfPax;
    private String status;

    public BookingRequestModel() {
        // Default constructor
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public int getNoOfPax() {
        return noOfPax;
    }

    public void setNoOfPax(int noOfPax) {
        this.noOfPax = noOfPax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
