package com.petcare.newteamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;


// 체중 일지 화면
public class RecordWeightActivity extends AppCompatActivity {

    private RecyclerView mrv_weight;
    private ArrayList<WeightItem> mWeightItems;
    private DBHelper mDBHelper;
    private WeightAdapter nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_weight);
        setInit();
    }

    private void setInit() {

        mDBHelper  = new DBHelper(this);
        mrv_weight = findViewById(R.id.rv_weight); // rv_weight 는 리사이클러뷰의 id
        mWeightItems = new ArrayList<>();

        // 구분선 생성
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrv_weight.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mrv_weight.addItemDecoration(dividerItemDecoration);

        String tempUserID = "tempUser"; // 임시 userID 설정 , TODO : userID 연결 이후에 삭제
        loadRecentDB(tempUserID); // TODO : tempUserID를 제거

    }

    private void loadRecentDB(String userID) {
        mWeightItems = mDBHelper.getPetWeight(userID);
        if(nAdapter == null){
            nAdapter = new WeightAdapter(mWeightItems, this);
            mrv_weight.setHasFixedSize(true);
            mrv_weight.setAdapter(nAdapter);
        }
    }
}