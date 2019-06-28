package com.exercise.android.lab03_q1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first  created.
     */
    private TextView tvUserName;
    private EditText etUserName;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** binding the UI's controls defined in main.xml to Java Code ***/
        tvUserName = findViewById(R.id.tvUserName);
        etUserName = findViewById(R.id.etUserName);
        btLogin = findViewById(R.id.btLogin);
    }

    public void onClick(View v) {
        String userName = etUserName.getText().toString();
        Toast.makeText(getApplicationContext(), "Hello " + userName,
                Toast.LENGTH_SHORT).show();
    }
}
