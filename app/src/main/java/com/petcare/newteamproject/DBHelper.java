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
    private static final int DB_VERSION = 1; // DB 버전 지정
    private static final String DB_NAME = "pet-weight.db"; // DB 이름 지정

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성될 때 호출되어 테이블이 없으면 테이블 생성
        // 테이블 이름은 petWeight , id와 weight, writeDate 컬럼을 가짐
        // weight 컬럼은 실수 형태의 몸무게 값을 저장함
        db.execSQL("CREATE TABLE IF NOT EXISTS petWeight (id INTEGER PRIMARY KEY AUTOINCREMENT, weight REAL NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT (몸무게 목록 조회)
    public ArrayList<WeightItem> get_petWeight() {
      ArrayList<WeightItem> weightItems = new ArrayList<>();

      SQLiteDatabase db = getReadableDatabase();
      Cursor cursor = db.rawQuery("SELECT * FROM petWeight ORDER BY writeDate DESC", null);
      if(cursor.getCount() != 0){
          // 조회할 데이터가 있을 때 수행
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

    // INSERT (몸무게 목록을 DB에 넣음)
    public void InsertWeight(Double _weight, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO petWeight(weight, writeDate) VALUES ('"+ _weight + "' , '"+ _writeDate + "')");
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

