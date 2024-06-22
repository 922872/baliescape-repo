package com.heroku.java.model;

import java.sql.Date;

public class PackageModel {
    private Integer packID;
    private String packName;
    private String packActivity;
    private double packPrice;

    public PackageModel() {

    }

    public int getPackID() {
        return packID;
    }

    public void setPackID(int packID) {
        this.packID = packID;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackActivity() {
        return packActivity;
    }

    public void setPackActivity(String packActivity) {
        this.packActivity = packActivity;
    }

    public double getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(double packPrice) {
        this.packPrice = packPrice;
    }

}
