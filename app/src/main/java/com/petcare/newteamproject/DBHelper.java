package com.petcare.newteamproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    // 어디서나 접근 가능하게 public 로 지정
    public void InsertWeight(Double _weight, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO petWeight(weight, writeDate) VALUES ('"+ _weight + "' , '"+ _writeDate + "')");
    }

    // UPDATE (몸무게 목록을 수정한다)
    public void updateWeight(Double _weight, String _writeDate, int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE petWeight SET weight= '" + _weight + "', writeDate= '" + _writeDate + "' WHERE id = '" + _id  + "'"); //  id 값을 활용해서 해당 몸무게 값을 수정

    }

    // DELETE (몸무게 목록을 제거한다)
    public void deleteWeight(int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM petWeight WHERE id = '" + _id +"'"); // id 값을 활용해 값 제거
    }
}

