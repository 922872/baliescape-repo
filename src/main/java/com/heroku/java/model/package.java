package com.heroku.java.model;

public class Package {
    private String packageID;
    private String packageName;
    private String packageActivity;
    private String packageType;
    private double packagePrice;

    // Getters and Setters
    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageActivity() {
        return packageActivity;
    }

    public void setPackageActivity(String packageActivity) {
        this.packageActivity = packageActivity;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(double packagePrice) {
        this.packagePrice = packagePrice;
    }
}
