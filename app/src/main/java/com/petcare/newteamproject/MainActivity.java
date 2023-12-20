package com.petcare.newteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private EditText weightInput;
    private long startTime;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recordWeightButton = findViewById(R.id.record_weight_button);
        Button recordWalkButton = findViewById((R.id.record_walk_button));
        Button startButton = findViewById(R.id.start_walk_button);
        Button stopButton = findViewById(R.id.stop_walk_button);
        Button saveWeightButton = findViewById(R.id.save_weight_button);
        weightInput = findViewById(R.id.weight_input);
        chronometer = findViewById(R.id.chronometer);

        dbHelper = new DBHelper(this);

        // TODO : 로그인한 사용자의 userID 가져오기

        // 체중저장 버튼 리스너
        saveWeightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveWeight(); // TODO -> saveWeight(loggedUserID);
            }
        });

        // 체중일지 버튼 리스너
        recordWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordWeightActivity.class);
                startActivity(intent);
            }
        });

        // 산책시작 버튼 리스너
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.elapsedRealtime();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        });

        // 산책종료 버튼 리스너
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWalk(); // TODO -> saveWalk(loggedUserID);
            }
        });

        // 산책 일지 버튼 리스너
        recordWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, RecordWalkActivity.class);
                startActivity(intent);
            }
        });

    }

    // TODO : 로그인한 사용자의 userID 가져와야함 -> private void saveWeight(String userID)
    private void saveWeight(){

        String weightStr = weightInput.getText().toString();

        if(!weightStr.isEmpty()) {
            try {

                double weight = Double.parseDouble(weightStr);
                String currentDate = getCurrentDate();
                String tempUserId = "tempUser";  // 임시 사용자 ID,  TODO : 삭제

                // 체중 데이터 전달, TODO : tempUserId 를 userID로 수정
                dbHelper.InsertWeight(tempUserId, weight, currentDate);

                Toast.makeText(MainActivity.this, "체중값이 저장되었습니다", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                // 입력란에 숫자가 입력되지 않은 경우
                Toast.makeText(MainActivity.this, "유효한 숫자를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        } else {
            // 입력란이 비어있는 경우
            Toast.makeText(MainActivity.this, "반려동물의 체중을 입력하세요", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO : 로그인한 사용자의 userID 가져와야함 -> private void saveWalk(String userID)
    private void saveWalk() {

        long duration = SystemClock.elapsedRealtime() - startTime;
        String durationTime = formatDuration(duration);
        chronometer.stop();

        String currentDate = getCurrentDate();
        String tempUserId = "tempUser"; // 임시 사용자 ID,  TODO : 삭제

        // 산책시간 데이터 전달, TODO : tempUserId 를 userID로 수정
        dbHelper.InsertWalk(tempUserId,durationTime, currentDate);
    }

    // 현재 날짜를 yyyy-mm-dd 형식 문자열로 변환
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return dateFormat.format(new Date());
    }

    // 스톱워치 지속시간을 문자열로 변환
    private String formatDuration(long duration) {
        int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((duration / (1000*60)) % 60);
        int hours   = (int) ((duration / (1000*60*60)) % 24);
        return String.format(Locale.KOREA, "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
