package com.heroku.java.model;

import java.sql.Date;

public class package {
    private Integer packID;
    private String packName;
    private String packActivity;
    private String packType;
    private double packPrice;

    public package() {

    }

    public Integer getPackID() {
        return packID;
    }

    public void setPackID(Integer packID) {
        this.packID = packID;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public void setPackActivity(String packActivity) {
        this.packActivity = packActivity;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public double getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(double packPrice) {
        this.packPrice = packPrice;
    }

}
