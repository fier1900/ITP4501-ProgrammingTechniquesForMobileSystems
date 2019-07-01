package com.exercise.android.morra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PlayerInfo";
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);
        tvWelcome = findViewById(R.id.tvWelcome);

        SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
        if (!playerInfo.contains("PlayerName")) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivityForResult(intent, 0);
        } else {
            String welcome = tvWelcome.getText().toString() + " " + playerInfo.getString("PlayerName", "") + "!";
            tvWelcome.setText(welcome);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == 0) {
            SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
            String welcome = tvWelcome.getText().toString() + " " + playerInfo.getString("PlayerName", "") + "!";
            tvWelcome.setText(welcome);
        }
    }


    public void onClickStartGame(View view) {

    }

    public void onClickViewHistory(View view) {

    }

    public void onClickEditInfo(View view) {

    }

    public void onClickQuit(View view) {
        this.finishAffinity();
    }
}
