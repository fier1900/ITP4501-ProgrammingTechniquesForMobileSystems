package com.exercise.android.morra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PlayerInfo";
    EditText edtxtPlayerName;
    EditText edtxtDOB;
    EditText edtxtPhone;
    EditText edtxtEmail;
    ImageView imgGameLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        edtxtPlayerName = findViewById(R.id.edtxtPlayerName);
        edtxtDOB = findViewById(R.id.edtxtDOB);
        edtxtPhone = findViewById(R.id.edtxtPhone);
        edtxtEmail = findViewById(R.id.edtxtEmail);
        imgGameLogo = findViewById(R.id.imgGameLogo);

        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.welcome_logo_anim);
        imgGameLogo.startAnimation(animShake);
    }

    public void onClickRegister(View view) {
        String playerName = edtxtPlayerName.getText().toString();
        String dob = edtxtDOB.getText().toString();
        String phone = edtxtPhone.getText().toString();
        String email = edtxtEmail.getText().toString();

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

            initialDB();

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            super.finish();
        }
    }

    public void initialDB() {
        // Create a database if it does not exist
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.exercise.android.morra/morraDB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db.execSQL("DROP TABLE IF EXISTS GamesLog;");

            db.execSQL("CREATE TABLE GamesLog (gameDate text, gameTime text, opponentName text, winOrLost text, PRIMARY KEY (gameDate, gameTime));");

            // Toast.makeText(this, "Table Seller is created and initialised.", Toast.LENGTH_SHORT).show();

//            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
