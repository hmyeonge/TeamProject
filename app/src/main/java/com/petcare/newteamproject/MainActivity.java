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

        // 위젯 참조
        Button recordWeightButton = findViewById(R.id.record_weight_button);
        Button recordWalkButton = findViewById((R.id.record_walk_button));
        Button startButton = findViewById(R.id.start_walk_button);
        Button stopButton = findViewById(R.id.stop_walk_button);
        Button saveWeightButton = findViewById(R.id.save_weight_button);
        weightInput = findViewById(R.id.weight_input);
        chronometer = findViewById(R.id.chronometer);

        // DBHelper 인스턴스화
        dbHelper = new DBHelper(this);

        /* TODO : 로그인한 사용자의 userID 가져와야함
         * SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
         * String loggedInUserID = prefs.getString("UserID", "defaultUserID");
         *
         * SharedPreferences 를 사용해 로그인한 사용자의 userID 가져옴
         * UserPrefs :  사용자의 설정을 저장하는 파일의 이름
         * getString 메소드 : 'UserID' 키에 해당하는 값을 반환, 값이 없는 경우 defaultUserID 를 기본값으로 반환
         */

        // 체중저장 버튼 리스너
        saveWeightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO -> saveWeight(loggedInUserID);
                saveWeight();
            }
        });

        // 체중일지 버튼 리스너
        recordWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인텐트를 사용해 RecordWeightActivity 시작
                Intent intent = new Intent(MainActivity.this, RecordWeightActivity.class);
                startActivity(intent);
            }
        });

        // 산책시작 버튼 리스너
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.elapsedRealtime(); // startTime 변수를 초기화
                chronometer.setBase(SystemClock.elapsedRealtime()); // 스톱워치가 현재 시점을 시작점으로 시간 계산 시작
                chronometer.start(); // 스톱워치 시작
            }
        });

        // 산책종료 버튼 리스너
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWalk();
            }
        });

        // 산책 일지 버튼 리스너
        recordWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인텐트를 사용해 RecordWalkActivity 라는 새 액티비티 시작
                Intent intent = new Intent( MainActivity.this, RecordWalkActivity.class);
                startActivity(intent);
            }
        });

    }

    // saveWeight 메소드 : 체중 데이터를 데이터베이스에 저장, TODO : 로그인한 사용자의 userID 가져오기 -> private void saveWeight(String userID)
    private void saveWeight(){

        // EditText 에서 입력된 체중 값을 문자열로 가져옴
        String weightStr = weightInput.getText().toString();

        // 입력된 문자열이 비어있지 않은지 확인
        if(!weightStr.isEmpty()) {
            try {

                // 문자열을 double 타입으로 변환해 체중 값으로 사용
                double weight = Double.parseDouble(weightStr);

                String currentDate = getCurrentDate(); // 현재 날짜를 문자열로 가져오는 메소드 호출
                Log.d("Current Date", currentDate); /// 현재 날짜를 로그로 출력

                // 임시 사용자 ID 설정, TODO : 이후 삭제
                String tempUserId = "tempUser";

                // 체중 데이터를 데이터베이스에 저장, TODO -> dbHelper.InsertWeight(userID, weight, currentDate);
                dbHelper.InsertWeight(tempUserId, weight, currentDate);

                Toast.makeText(MainActivity.this, "체중값이 저장되었습니다", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                // 몸무게 입력란에 숫자를 입력하지 않는 경우 토스트 메시지를 띄움
                Toast.makeText(MainActivity.this, "유효한 숫자를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        } else {
            // EditText 값이 비어있는 경우 오류 메시지 출력
            Toast.makeText(MainActivity.this, "반려동물의 체중을 입력하세요", Toast.LENGTH_SHORT).show();
        }
    }

    // saveWalk 메소드 : 산책시간 데이터를 데이터베이스에 저장, TODO : 로그인한 사용자의 userID 가져와야함 -> private void saveWalk(String userID)
    private void saveWalk() {
        long duration = SystemClock.elapsedRealtime() - startTime; // 스톱워치 지속시간 계산
        String formattedDuration = formatDuration(duration); // 지속시간을 문자열로 변환
        chronometer.stop(); // 타이머 중지

        String currentDate = getCurrentDate(); // // 현재 날짜를 문자열로 가져오는 메소드 호출
        Log.d("Current Date", currentDate); // 현재 날짜를 로그로 출력

        // 임시 사용자 ID 설정, TODO : 이후 삭제
        String tempUserId = "tempUser";

        // 산책시간 데이터를 데이터베이스에 저장, TODO -> dbHelper.InsertWalk(userID, formattedDuration, currentDate);
        dbHelper.InsertWalk(tempUserId, formattedDuration, currentDate);
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
        return String.format(Locale.KOREA, "%02d:%02d:%02d", hours, minutes, seconds); // 한국 로케일로 설정
    }

}
