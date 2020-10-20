package com.color.memory;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    private boolean isActivityRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isActivityRunning) {
                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                }
                finish();

            }
        }, 3000);

    }

    @Override
    protected void onPause() {
        isActivityRunning = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        isActivityRunning = true;
        super.onResume();
    }
}
