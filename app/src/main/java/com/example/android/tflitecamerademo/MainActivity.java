package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkPose(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void practice(View view){
        Intent intent = new Intent(this, PracticeActivity.class);
        startActivity(intent);
    }

    public void opencalendar(View view){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
