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

        // 초기화
        mDBHelper = new DBHelper(this);
        mrv_walk = findViewById(R.id.rv_walk); // rv_walk 는 리사이클러뷰의 id
        mWalkItems = new ArrayList<>();

        // 구분선 생성 : DividerItemDecoration 객체를 생성하고 리사이클러뷰에 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrv_walk.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mrv_walk.addItemDecoration(dividerItemDecoration);

        String tempUserID = "tempUser"; // 임시 userID 설정 , TODO : userID 연결 이후에 삭제
        loadRecentDB(tempUserID); // TODO : tempUserID를 제거
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadRecentDB(String userID) {
        // 데이터베이스에 저장된 산책시간 데이터를 조회해 ArrayList<WalkItem> 형태로 반환
        mWalkItems = mDBHelper.getPetWalkByUser(userID);
        Log.d("RecordWalkActivity", "Loaded " + mWalkItems.size() + " walk items for user: " + userID);
        if (nAdapter == null) {
            // nAdapter 가 초기화되지 않았으면 CustomAdapter 인스턴스를 생성
            // 해당 어댑터는 mWalkItems 리스트를 사용해 RecyclerView 에 데이터 표시
            nAdapter = new WalkAdapter(mWalkItems, this);
            mrv_walk.setHasFixedSize(true);
            mrv_walk.setAdapter(nAdapter);
            // RecyclerView 에 어댑터를 설정
            mrv_walk.setAdapter(nAdapter);
        } else {
            // 기존 어댑터가 있으면 데이터가 변경되었음을 알림
            nAdapter.notifyDataSetChanged();
        }
        }
    }

