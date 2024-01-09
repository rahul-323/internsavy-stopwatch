package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTime;
    private Button btnStart, btnStop, btnHold;
    private long startTime = 0;
    private final Handler handler = new Handler();
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnHold = findViewById(R.id.btnHold);

        btnStop.setEnabled(false);
        btnHold.setEnabled(false);
    }

    public void startTimer(View view) {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            handler.postDelayed(updateTimer, 0);
            isRunning = true;

            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            btnHold.setEnabled(true);
        }
    }

    public void stopTimer(View view) {
        if (isRunning) {
            handler.removeCallbacks(updateTimer);
            isRunning = false;

            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
            btnHold.setEnabled(false);
        }
    }

    public void holdTimer(View view) {
        if (isRunning) {
            handler.removeCallbacks(updateTimer);
            isRunning = false;

            btnStart.setEnabled(true);
            btnStop.setEnabled(true);
            btnHold.setEnabled(false);
        } else {
            // If the stopwatch is on hold, restart it
            startTime = System.currentTimeMillis() - (System.currentTimeMillis() - startTime);
            handler.postDelayed(updateTimer, 0);
            isRunning = true;

            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            btnHold.setEnabled(true);
        }
    }


    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (elapsedTime / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;

            seconds = seconds % 60;
            minutes = minutes % 60;

            String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            tvTime.setText(time);

            handler.postDelayed(this, 1000);
        }
    };
}