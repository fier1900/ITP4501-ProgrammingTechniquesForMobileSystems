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

        onReturnCheckNewPlayer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && (requestCode == 0 || requestCode == 1)) {
            onReturnCheckNewPlayer();
        }
    }

    protected void onReturnCheckNewPlayer(){
        SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
        if (!playerInfo.contains("PlayerName")) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivityForResult(intent, 0);
        } else {
            String welcome = "Welcome, " + playerInfo.getString("PlayerName", "") + "!";
            tvWelcome.setText(welcome);
        }
    }

    public void onClickStartGame(View view) {
        Intent intent = new Intent(this, GameplayActivity.class);
        startActivity(intent);
    }

    public void onClickViewHistory(View view) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void onClickEditInfo(View view) {
        Intent intent = new Intent(this, UpdateInfoActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onClickQuit(View view) {
        this.finishAffinity();
    }
}
