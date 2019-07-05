package com.exercise.android.lab08_q2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private DrawableView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = new DrawableView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }
}
