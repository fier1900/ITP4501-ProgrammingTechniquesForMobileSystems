package com.exercise.android.lab02_q2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText etValue1;
    private EditText etValue2;
    private TextView tvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValue1 = (EditText) findViewById(R.id.value1);
        etValue2 = (EditText) findViewById(R.id.value2);
        tvAnswer = (TextView) findViewById(R.id.answer);
    }

    public void onClick(View view) {
        double value1 = Double.parseDouble(etValue1.getText().toString());
        double value2 = Double.parseDouble(etValue2.getText().toString());

        tvAnswer.setText("Answer = " + (value1 + value2));
    }
}
