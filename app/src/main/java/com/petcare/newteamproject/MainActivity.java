package com.petcare.newteamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private Button startButton;
    private Button stopButton;
    private Button saveWeightButton;
    private EditText weightInput;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위젯 참조
        chronometer = findViewById(R.id.chronometer);
        startButton = findViewById(R.id.start_walk_button);
        stopButton = findViewById(R.id.stop_walk_button);
        saveWeightButton = findViewById(R.id.save_weight_button);
        weightInput = findViewById(R.id.weight_input);
        Button recordWeightButton = findViewById(R.id.record_weight_button);

        // DBHelper 인스턴스화
        dbHelper = new DBHelper(this);

        // 산책 시작 버튼 리스너 설정
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime()); // 타이머 초기화
                chronometer.start(); // 타이머 시작
            }
        });

        // 산책 종료 버튼 리스너 설정
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop(); // 타이머 중지
            }
        });

        // 몸무게 저장 버튼 리스너 설정
        saveWeightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveWeight();
            }
        });

        // 몸무게 일지 버튼 리스너 설정
        recordWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인텐트를 사용해 RecordWeightActivity 라는 새 액티비티 시작
                Intent intent = new Intent(MainActivity.this, RecordWeightActivity.class);
                startActivity(intent);
            }
        });

    }

    // saveWeight 메소드 사용해 사용자가 입력한 몸무게 검증
    private void saveWeight(){
        // EditText 에서 입력된 몸무게 값을 문자열로 가져옴
        String weightStr = weightInput.getText().toString();

        // 입력된 문자열이 비어있지 않은지 확인
        if(!weightStr.isEmpty()) {
            try {
                Toast.makeText(MainActivity.this, "몸무게값이 저장되었습니다", Toast.LENGTH_SHORT).show();
                // 문자열을 double 타입으로 변환해 몸무게 값으로 사용
                double weight = Double.parseDouble(weightStr);

                // 현재 날짜를 문자열로 가져오는 메소드 호출
                String currentDate = getCurrentDate();

                // DBHelper 인스턴스로 데이터베이스에 몸무게랑 날짜 저장
                dbHelper.InsertWeight(weight, currentDate);
            } catch (NumberFormatException e) {
                // EditText 에 숫자를 입력하지 않는 경우 토스트 메시지를 띄움
                Toast.makeText(MainActivity.this, "유효한 숫자를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        } else {
            // EditText 값이 비어있는 경우 오류 메시지 출력
            Toast.makeText(MainActivity.this, "반려동물의 몸무게를 입력하세요", Toast.LENGTH_SHORT).show();
        }
    }

    // 현재 날짜를 yyyy-mm-dd 형식 문자열로 변환
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
