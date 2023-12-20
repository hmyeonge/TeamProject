package com.petcare.newteamproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "pet-weight.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS petWeight (id INTEGER PRIMARY KEY AUTOINCREMENT, weight REAL NOT NULL, writeDate TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS petWalk (id INTEGER PRIMARY KEY AUTOINCREMENT , userID TEXT NOT NULL, time TEXT NOT NULL , writeDate TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 테이블 이름 petWeight 변경, userID 타입 변경
        if (oldVersion < 2) {

            db.execSQL("CREATE TABLE temp_petWeight (id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT NOT NULL, weight REAL NOT NULL, writeDate TEXT NOT NULL)");
            db.execSQL("INSERT INTO temp_petWeight (id, userID, weight, writeDate) SELECT id, CAST(userID AS TEXT), weight, writeDate FROM petWeight");
            db.execSQL("DROP TABLE petWeight");
            db.execSQL("ALTER TABLE temp_petWeight RENAME TO petWeight");
        }
        // 추가 업그레이드 작업
    }

    // 체중 데이터 조회
    public ArrayList<WeightItem> getPetWeight(String userID) {
        ArrayList<WeightItem> weightItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM petWeight WHERE userID = ? ORDER BY writeDate DESC", new String[] {userID});
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") Double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
            @SuppressLint("Range") String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

            // 데이터를 WeightItem 객체로 변환
            WeightItem weightItem = new WeightItem(id, userID, weight, writeDate);
            weightItems.add(weightItem);
        }
        cursor.close();
        return weightItems;
    }

    // 체중 데이터 DB 삽입
    public void InsertWeight(String userID, Double weight, String writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO petWeight(userID, weight, writeDate) VALUES ('" + userID + "', " + weight + " , '" + writeDate + "')");
    }


    // 산책 데이터 조회
    public ArrayList<WalkItem> getPetWalk(String userID) {
        ArrayList<WalkItem> walkItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM petWalk WHERE userID = ? ORDER BY writeDate DESC", new String[] {userID});
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                // 데이터를 WalkItem 객체로 변환
                WalkItem walkItem = new WalkItem(id, userID, time, writeDate);
                walkItems.add(walkItem);
            }
        }
        cursor.close();
        return walkItems;
    }

    // 산책 데이터 DB 삽입
    public void InsertWalk(String userID, String time, String writeDate){
        SQLiteDatabase db = getWritableDatabase(); // SQLiteDatabase 객체 가져옴
        db.execSQL("INSERT INTO petWalk (userID, time, writeDate) VALUES ('" + userID + "', '" + time + "', '" + writeDate + "')");
    }

}

