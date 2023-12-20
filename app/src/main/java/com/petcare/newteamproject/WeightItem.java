package com.petcare.newteamproject;

public class WeightItem
{
    private int id;
    private String userID;
    private Double weight;
    private String writeDate;

    public WeightItem()
    {
    }

    public WeightItem(int id, String userID, Double weight, String writeDate) {
        this.id = id;
        this.userID = userID;
        this.weight = weight;
        this.writeDate = writeDate;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getWriteDate() {
        return writeDate;
    }
    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
}
