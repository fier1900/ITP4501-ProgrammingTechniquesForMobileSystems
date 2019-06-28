package com.exercise.android.lab05_q2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 3434;
    private int trial = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickHandler(View view) {
        trial++;
        EditText ETname = findViewById(R.id.ETname);
        String name = ETname.getText().toString();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("trial", String.valueOf(trial));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("playTime")) {
                TextView tv = (TextView) findViewById(R.id.TVmsg);
                String playedTime = "You have played for " + data.getExtras().getString("playTime") + " second(s)";
                tv.setText(playedTime);
            }
        }
    }
}

