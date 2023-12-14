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
    private RecyclerView mBtn_write;
    private ArrayList<WeightItem> mWeightItems;
    private DBHelper mDBHelper;
    private CustomAdapter nAdapter;

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

        // 구분선 생성 : DividerItemDecoration 객체를 생성하고 리사이클러뷰에 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrv_weight.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mrv_weight.addItemDecoration(dividerItemDecoration);

        String tempUserID = "tempUser"; // 임시 userID 설정 , TODO : userID 연결 이후에 삭제
        loadRecentDB(tempUserID); // TODO : tempUserID를 제거

    }

    // DB 로부터 저장된 모든 체중 데이터를 로드하고, 이 데이터를 RecyclerView 를 통해 표시함
    private void loadRecentDB(String userID) {
        // 데이터베이스에 저장된 체중 데이터를 조회해 ArrayList<WeightItem> 형태로 반환
        mWeightItems = mDBHelper.getPetWeightByUser(userID);
        if(nAdapter == null){
            // nAdapter 가 초기화되지 않았으면 CustomAdapter 인스턴스를 생성
            // 해당 어댑터는 mWeightItems 리스트를 사용해 RecyclerView 에 데이터 표시
            nAdapter = new CustomAdapter(mWeightItems, this);
            mrv_weight.setHasFixedSize(true);
            // RecyclerView 에 어댑터를 설정
            mrv_weight.setAdapter(nAdapter);
        }
    }

}