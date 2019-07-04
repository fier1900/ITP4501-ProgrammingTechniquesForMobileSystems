package com.exercise.android.morra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateInfoActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PlayerInfo";
    EditText edtxtPlayerName;
    EditText edtxtDOB;
    EditText edtxtPhone;
    EditText edtxtEmail;

    String playerName;
    String dob;
    String phone;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_info);

        edtxtPlayerName = findViewById(R.id.edtxtPlayerName);
        edtxtDOB = findViewById(R.id.edtxtDOB);
        edtxtPhone = findViewById(R.id.edtxtPhone);
        edtxtEmail = findViewById(R.id.edtxtEmail);

        SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
        playerName = playerInfo.getString("PlayerName", "");
        dob = playerInfo.getString("DOB", "");
        phone = playerInfo.getString("Phone", "");
        email = playerInfo.getString("Email", "");

        edtxtPlayerName.setText(playerName);
        edtxtDOB.setText(dob);
        edtxtPhone.setText(phone);
        edtxtEmail.setText(email);
    }

    public void onClickDelete(View view) {
        SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor playerInfoEditor = playerInfo.edit();
        playerInfoEditor.remove("PlayerName");
        playerInfoEditor.remove("DOB");
        playerInfoEditor.remove("Phone");
        playerInfoEditor.remove("Email");
        playerInfoEditor.commit();

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickUpdate(View view) {
        playerName = edtxtPlayerName.getText().toString();
        dob = edtxtDOB.getText().toString();
        phone = edtxtPhone.getText().toString();
        email = edtxtEmail.getText().toString();

        if (playerName.equals("") || dob.equals("") || phone.equals("") || email.equals("")) {
            Toast.makeText(this, "Please Fill In All Your Information!", Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor playerInfoEditor = playerInfo.edit();
            playerInfoEditor.putString("PlayerName", playerName);
            playerInfoEditor.putString("DOB", dob);
            playerInfoEditor.putString("Phone", phone);
            playerInfoEditor.putString("Email", email);
            playerInfoEditor.commit();

            Toast.makeText(this, "Updated Info Successfully!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
