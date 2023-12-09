package com.petcare.newteamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordWeightActivity extends AppCompatActivity {

    private RecyclerView mrv_weight;
    private RecyclerView mBtn_write;
    private ArrayList<WeightItem> mWeightItems;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 보여주는 레이아웃 뷰
        setContentView(R.layout.activity_record_weight);

        setInit();
    }

    // 코드 가독성을 위해 setInit 메소드 따로 생성
    private void setInit() {
        // 초기화 작업
        mDBHelper  = new DBHelper(this);
        mrv_weight = findViewById(R.id.rv_weight); // rv_weight 는 리사이클러뷰의 id
        mWeightItems = new ArrayList<>();

    }

}