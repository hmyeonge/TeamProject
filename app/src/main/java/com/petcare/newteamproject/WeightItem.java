package com.petcare.newteamproject;

public class WeightItem
{
    private int id; // 몸무게 항목의 고유 ID
    private Double weight; // 몸무게 값
    private String writeDate; // 작성 날짜

    public WeightItem()
    {
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
}
