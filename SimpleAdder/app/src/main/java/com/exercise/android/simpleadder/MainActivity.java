package com.exercise.android.simpleadder;

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

        etValue1 = (EditText) findViewById(R.id.etValue1);
        etValue2 = (EditText) findViewById(R.id.etValue2);
        tvAnswer = (TextView) findViewById(R.id.tvAnswer);
    }

    public void onClick(View view) {
        double value1 = Double.parseDouble(etValue1.getText().toString());
        double value2 = Double.parseDouble(etValue2.getText().toString());

        tvAnswer.setText(value1 + " + " + value2 + " = " + (value1 + value2));
    }
}
