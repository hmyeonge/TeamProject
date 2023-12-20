package com.petcare.newteamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

// 산책일지 화면
public class RecordWalkActivity extends AppCompatActivity {

    private RecyclerView mrv_walk;
    private ArrayList<WalkItem> mWalkItems;
    private DBHelper mDBHelper;
    private WalkAdapter nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_walk);
        setInit();
    }

    private void setInit() {

        mDBHelper = new DBHelper(this);
        mrv_walk = findViewById(R.id.rv_walk); // rv_walk 는 리사이클러뷰의 id
        mWalkItems = new ArrayList<>();

        // 구분선 생성
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrv_walk.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mrv_walk.addItemDecoration(dividerItemDecoration);

        String tempUserID = "tempUser"; // 임시 userID 설정 , TODO : userID 연결 이후에 삭제
        loadRecentDB(tempUserID); // TODO : tempUserID를 제거
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadRecentDB(String userID) {

        mWalkItems = mDBHelper.getPetWalk(userID);

        if (nAdapter == null) {
            nAdapter = new WalkAdapter(mWalkItems, this);
            mrv_walk.setHasFixedSize(true);
            mrv_walk.setAdapter(nAdapter);
            mrv_walk.setAdapter(nAdapter);
        } else {
            nAdapter.notifyDataSetChanged();
         }
       }
    }

