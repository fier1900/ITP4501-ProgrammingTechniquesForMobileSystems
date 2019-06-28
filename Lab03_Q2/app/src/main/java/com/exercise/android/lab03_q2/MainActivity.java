package com.exercise.android.lab03_q2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     **/
    private CheckBox cbCream, cbSugar;
    private RadioGroup rgCoffeeType;
    private RadioButton rbLatte, rbMocha, rbCappuccino;
    private Button btPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*** binding the UI's controls defined in main.xml to Java Code ***/
        cbCream = findViewById(R.id.cbCream);
        cbSugar = findViewById(R.id.cbSugar);
        rgCoffeeType = findViewById(R.id.rgCoffeeType);
        rbLatte = findViewById(R.id.rbLatte);
        rbMocha = findViewById(R.id.rbMocha);
        rbCappuccino = findViewById(R.id.rbCappuccino);
    }

    public void btPayOnClick(View v) {
        /*** TO BE COMPLETED ***/
        if (rgCoffeeType.getCheckedRadioButtonId() != -1) {
            RadioButton coffeeRB = findViewById(rgCoffeeType.getCheckedRadioButtonId());
            String result = coffeeRB.getText().toString() + " Coffee";

            if (cbCream.isChecked()) {
                result += " & " + cbCream.getText().toString();
            }
            if (cbSugar.isChecked()) {
                result += " & " + cbSugar.getText().toString();
            }

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Please select coffee type!", Toast.LENGTH_SHORT).show();
        }
    }
}

