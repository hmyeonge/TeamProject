package com.petcare.newteamproject;

public class WeightItem
{
    private int id; // 체중 항목의 고유 ID
    private String userID; // 사용자 ID
    private Double weight; // 체중 값
    private String writeDate; // 작성 날짜

    public WeightItem()
    {
    }

    public WeightItem(int id, String userID, Double weight, String writeDate) {
        this.id = id;
        this.userID = userID;
        this.weight = weight;
        this.writeDate = writeDate;
    }

    // Getter 및 Setter 메소드
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
