package com.exercise.android.morra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PlayerInfo";
    EditText edtxtPlayerName;
    EditText edtxtDOB;
    EditText edtxtPhone;
    EditText edtxtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);

        edtxtPlayerName = findViewById(R.id.edtxtPlayerName);
        edtxtDOB = findViewById(R.id.edtxtDOB);
        edtxtPhone = findViewById(R.id.edtxtPhone);
        edtxtEmail = findViewById(R.id.edtxtEmail);
    }

    public void onClickRegister(View view) {
        String playerName = edtxtPlayerName.getText().toString();
        String dob = edtxtDOB.getText().toString();
        String phone = edtxtPhone.getText().toString();
        String email = edtxtEmail.getText().toString();

        if (playerName.equals("") || dob.equals("") || phone.equals("") || email.equals("")) {
            Toast.makeText(this,"Please Fill In All Your Information!",Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor playerInfoEditor = playerInfo.edit();
            playerInfoEditor.putString("PlayerName", playerName);
            playerInfoEditor.putString("DOB", dob);
            playerInfoEditor.putString("Phone", phone);
            playerInfoEditor.putString("Email", email);
            playerInfoEditor.commit();

            Intent intent = new Intent();
            intent.putExtra("playerName", playerName);
            setResult(RESULT_OK, intent);
            super.finish();
        }
    }
}
