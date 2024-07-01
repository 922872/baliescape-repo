package com.heroku.java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Booking {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bookingID;
  private String package;
  private String packageType;
  private int noOfPack;
  private String duration;

  // Getters and Setters
  public Integer getBookingID() {
    return bookingID;
  }

  public void setBookingID(Integer bookingID) {
    this.bookingID = bookingID;
  }

  public String getPackage() {
    return package;
  }

  public void setPackage(String package) {
    this.package = package;
  }

  public String getPackageType() {
    return packageType;
  }

  public void setPackageType(String packageType) {
    this.packageType = packageType;
  }

  public int getNoOfPack() {
    return noOfPack;
  }

  public void setNoOfPack(int noOfPack) {
    this.noOfPack = noOfPack;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }
}
