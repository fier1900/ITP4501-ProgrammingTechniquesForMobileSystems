package com.exercise.android.morra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String PREFS_NAME = "PlayerInfo";
    EditText edtxtPlayerName;
    EditText edtxtDOB;
    EditText edtxtPhone;
    EditText edtxtEmail;
    ImageView imgGameLogo;
    String playerCountry;

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

        Spinner spinner = findViewById(R.id.spinCountry);
        ArrayAdapter<CharSequence> countryList = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.country_list, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(countryList);
        spinner.setOnItemSelectedListener(this);
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
            playerInfoEditor.putString("Country", playerCountry);
            playerInfoEditor.commit();

            initialDB();

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            super.finish();
        }
    }

    public void initialDB() {
        try {
            // Create a database if it does not exist
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.exercise.android.morra/morraDB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db.execSQL("DROP TABLE IF EXISTS GamesLog;");

            db.execSQL("CREATE TABLE GamesLog (gameDate text, gameTime text, opponentName text, country text, winOrLost text, PRIMARY KEY (gameDate, gameTime));");
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(19);
        playerCountry = ((TextView) parent.getChildAt(0)).getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed()
    {
        this.finishAffinity();
    }
}
