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

    private static final int DB_VERSION = 3; // DB 버전 지정
    private static final String DB_NAME = "pet-weight.db"; // DB 이름 지정

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성될 때 호출되어 테이블이 없으면 테이블 생성
        // petWeight 테이블 : id , weight , writeDate 컬럼을 가짐 (이후 userID 컬럼 추가)
        // petWalk 테이블 : id , userID , time , writeDate 컬럼을 가짐
        db.execSQL("CREATE TABLE IF NOT EXISTS petWeight (id INTEGER PRIMARY KEY AUTOINCREMENT, weight REAL NOT NULL, writeDate TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS petWalk (id INTEGER PRIMARY KEY AUTOINCREMENT , userID TEXT NOT NULL, time TEXT NOT NULL , writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // 임시 테이블 생성
            db.execSQL("CREATE TABLE temp_petWeight (id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT NOT NULL, weight REAL NOT NULL, writeDate TEXT NOT NULL)");

            // 기존 데이터를 임시 테이블로 복사
            // 기존 userID 가 INTEGER 로 설정되어 있으므로 이를 문자열로 변환
            db.execSQL("INSERT INTO temp_petWeight (id, userID, weight, writeDate) SELECT id, CAST(userID AS TEXT), weight, writeDate FROM petWeight");

            // 기존 테이블 삭제
            db.execSQL("DROP TABLE petWeight");

            // 임시 테이블 이름 변경
            db.execSQL("ALTER TABLE temp_petWeight RENAME TO petWeight");
        }
        // 추가 업그레이드 작업
    }

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
      cursor.close(); // Cursor 객체를 사용해 쿼리 결과를 처리, close( ) 메소드 호출로 리소스 해제
      return weightItems;
    }

    // INSERT (체중 데이터를 DB에 삽입)
    // TODO : userID
    public void InsertWeight(String userID, Double weight, String writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO petWeight(userID, weight, writeDate) VALUES ('" + userID + "', " + weight + " , '" + writeDate + "')");
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
        cursor.close(); // Cursor 객체를 사용해 쿼리 결과를 처리, close( ) 메소드 호출로 리소스 해제
        return walkItems;
    }

    // INSERT (산책 데이터 삽입)
    public void InsertWalk(String userID, String time, String writeDate){
        SQLiteDatabase db = getWritableDatabase(); // SQLiteDatabase 객체 가져옴
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

