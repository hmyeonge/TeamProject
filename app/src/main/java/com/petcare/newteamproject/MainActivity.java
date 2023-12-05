package com.petcare.newteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 산책 시작, 산책 종료 위젯 참조 가져옴
        chronometer = findViewById(R.id.chronometer);
        startButton = findViewById(R.id.start_walk_button);
        stopButton = findViewById(R.id.stop_walk_button);

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
    }
}
