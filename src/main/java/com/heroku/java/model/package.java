package com.heroku.java.model;

public class package {
   
    private int packID;
    private String packName;
    private String packType;
    private double packPrice;
    

    public package(){
        
    }

    public int getPackID() {
        return this.packID;
    }

    public void setPackID(int packID) {
        this.packID = packID;
    }
    
    public String getPackName() {
        return this.packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackType() {
        return this.packType;
    }

    public void setPackType(String packType ) {
        this.packType = packType;
    }

    public double getPackPrice() {
        return this.packPrice;
    }

    public void setPackPrice(double packPrice) {
        this.packPrice = packPrice;
    }


}