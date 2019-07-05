package com.exercise.android.lab08_q3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View drawView = new DrawableView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }
}
