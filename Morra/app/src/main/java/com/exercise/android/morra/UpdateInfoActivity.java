package com.exercise.android.morra;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class UpdateInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String PREFS_NAME = "PlayerInfo";
    EditText edtxtPlayerName;
    EditText edtxtDOB;
    EditText edtxtPhone;
    EditText edtxtEmail;
    Spinner spinCountry;
    ImageView imgGameLogo;

    String playerName;
    String dob;
    String phone;
    String email;
    String playerCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update_info);

        edtxtPlayerName = findViewById(R.id.edtxtPlayerName);
        edtxtDOB = findViewById(R.id.edtxtDOB);
        edtxtPhone = findViewById(R.id.edtxtPhone);
        edtxtEmail = findViewById(R.id.edtxtEmail);
        imgGameLogo = findViewById(R.id.imgGameLogo);
        spinCountry = findViewById(R.id.spinCountry);

        ArrayAdapter<CharSequence> countryList = ArrayAdapter.createFromResource(UpdateInfoActivity.this, R.array.country_list, android.R.layout.simple_spinner_dropdown_item);
        spinCountry.setAdapter(countryList);
        spinCountry.setOnItemSelectedListener(this);

        SharedPreferences playerInfo = getSharedPreferences(PREFS_NAME, 0);
        playerName = playerInfo.getString("PlayerName", "");
        dob = playerInfo.getString("DOB", "");
        phone = playerInfo.getString("Phone", "");
        email = playerInfo.getString("Email", "");
        int spinCountryIndex = 0;
        switch (playerInfo.getString("Country", "")) {
            case "Hong Kong":
                spinCountryIndex = 0;
                break;
            case "China":
                spinCountryIndex = 1;
                break;
            case "Japan":
                spinCountryIndex = 2;
                break;
            case "USA":
                spinCountryIndex = 3;
                break;
            case "UK":
                spinCountryIndex = 4;
                break;
        }
        spinCountry.setSelection(spinCountryIndex);

        edtxtPlayerName.setText(playerName);
        edtxtDOB.setText(dob);
        edtxtPhone.setText(phone);
        edtxtEmail.setText(email);

        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.welcome_logo_anim);
        imgGameLogo.startAnimation(animShake);
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
            playerInfoEditor.putString("Country", playerCountry);
            playerInfoEditor.commit();

            Toast.makeText(this, "Updated Info Successfully!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
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
}
