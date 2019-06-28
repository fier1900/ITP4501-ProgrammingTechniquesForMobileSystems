package com.exercise.android.lab03_q3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private RadioGroup rgOperator;
    private RadioButton rbAdd, rbSub, rbMul, rbDiv;
    private Button btnCalculate;
    private EditText etNum1, etNum2;
    private TextView tvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*** TO BE COMPLETED ***/
        rgOperator = findViewById(R.id.rgOperator);
        rbAdd = findViewById(R.id.rbAdd);
        rbSub = findViewById(R.id.rbSub);
        rbMul = findViewById(R.id.rbMul);
        rbDiv = findViewById(R.id.rbDiv);
        btnCalculate = findViewById(R.id.btnCalculate);
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        tvAnswer = findViewById(R.id.tvAnswer);
    }

    public void btnCalculateOnClick(View view) {
        /*** TO BE COMPLETED ***/

        RadioButton operatorRB = findViewById(rgOperator.getCheckedRadioButtonId());

        double result = 0;
        double num1 = Integer.parseInt(etNum1.getText().toString());
        double num2 = Integer.parseInt(etNum2.getText().toString());

        switch (operatorRB.getText().toString()) {
            case ("+"):
                result = num1 + num2;
                break;
            case ("-"):
                result = num1 - num2;
                break;
            case ("x"):
                result = num1 * num2;
                break;
            case ("/"):
                result = num1 / num2;
                break;
        }

        String resultText = " " + String.valueOf(result);
        tvAnswer.setText(resultText);
    }
}
