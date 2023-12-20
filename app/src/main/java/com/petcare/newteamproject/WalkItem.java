package com.petcare.newteamproject;

public class WalkItem {

    private int id;
    private String userID;
    private String time;
    private String writeDate;


    public WalkItem() {

    }

    public WalkItem(int id, String userID, String time, String writeDate) {
        this.id = id;
        this.userID = userID;
        this.time = time;
        this.writeDate = writeDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserID() { return userID;}
    public void setUserID(String userID) { this.userID = userID; }

    public String getTime() { return time; }
    public void setTime(String time)  {this.time = time; }

    public String getWriteDate() { return writeDate; }
    public void setWriteDate(String writeDate) { this.writeDate = writeDate; }
}
