package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.os.Bundle;

public class PracticeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, Practice2BasicFragment.newInstance())
                    .commit();
        }
    }
}
