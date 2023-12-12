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

    // 클래스 외부에서는 호출을 못하도록 private 로 지정
    private static final int DB_VERSION = 2; // DB 버전 지정
    private static final String DB_NAME = "pet-weight.db"; // DB 이름 지정, 나중에 이름 수정 !

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // petWeight, petWalk 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성될 때 호출되어 테이블이 없으면 테이블 생성
        // petWeight 테이블 : id , weight , writeDate 컬럼을 가짐 (아래에서 userID 컬럼 추가)
        // petWalk 테이블 : id , userID , time , writeDate 컬럼을 가짐
        db.execSQL("CREATE TABLE IF NOT EXISTS petWeight (id INTEGER PRIMARY KEY AUTOINCREMENT, weight REAL NOT NULL, writeDate TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS petWalk (id INTEGER PRIMARY KEY AUTOINCREMENT , userID TEXT NOT NULL, time TEXT NOT NULL , writeDate TEXT NOT NULL)");
    }

    // DB 업그레이드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // 버전에 따라 다양한 업그레이드 작업을 수행
            switch (oldVersion) {
                case 1:
                    // petWeight 테이블에 userID 컬럼 추가
                    db.execSQL("ALTER TABLE petWeight ADD COLUMN userID INTEGER NOT NULL DEFAULT 1");
                case 2:
                    // 이후 업그레이드 작업을 수행
            }
        }
    }

    // 체중 데이터베이스의 SELECT 문과 INSERT 문
    // SELECT (체중 데이터 조회)
    public ArrayList<WeightItem> get_petWeight() {
      ArrayList<WeightItem> weightItems = new ArrayList<>();

      SQLiteDatabase db = getReadableDatabase();
      Cursor cursor = db.rawQuery("SELECT * FROM petWeight ORDER BY writeDate DESC", null);
      if(cursor.getCount() != 0){
          while (cursor.moveToNext()){
              @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
              @SuppressLint("Range") Double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
              @SuppressLint("Range") String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

              WeightItem weightItem = new WeightItem();
              weightItem.setId(id);
              weightItem.setWeight(weight);
              weightItem.setWriteDate(writeDate);
              weightItems.add(weightItem);
          }
       }
      cursor.close();
      return weightItems;
    }

    // INSERT (체중 데이터를 DB에 삽입)
    public void InsertWeight(Double _weight, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO petWeight(weight, writeDate) VALUES ('"+ _weight + "' , '"+ _writeDate + "')");
    }

    // 산책 데이터베이스의 SELECT 문과 INSERT 문
    // SELECT (산책 데이터 조회)
    public ArrayList<WalkItem> getPetWalk() {
        ArrayList<WalkItem> walkItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM petWalk ORDER BY writeDate DESC", null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String userID = cursor.getString(cursor.getColumnIndex("userID"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                WalkItem walkItem = new WalkItem(id, userID, time, writeDate);
                walkItems.add(walkItem);
            }
        }
        cursor.close();
        return walkItems;
    }

    // INSERT (산책 데이터 삽입)
    public void InsertWalk(String userID, String time, String writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO petWalk (userID, time, writeDate) VALUES ('" + userID + "', '" + time + "', '" + writeDate + "')");
    }


    // 로그
    public SQLiteDatabase getWritableDatabase() {
        Log.d("DBHelper", "데이터베이스 열기");
        return super.getWritableDatabase();
    }

    @Override
    public void close() {
        Log.d("DBHelper", "데이터베이스 닫기");
        super.close();
    }

}

