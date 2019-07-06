package com.exercise.android.morra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PlayerInfo";
    TextView tvWelcome;
    ImageView imgGameLogo;
    MediaPlayer myBGM;
    ImageButton muteToggle;
    Drawable Muted, notMuted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);

        myBGM = MediaPlayer.create(this, R.raw.bgm_welcome);
        myBGM.setLooping(true);
        myBGM.start();

        tvWelcome = findViewById(R.id.tvWelcome);
        imgGameLogo = findViewById(R.id.imgGameLogo);
        muteToggle = findViewById(R.id.muteToggle);
        Resources res = getResources();
        Muted = ResourcesCompat.getDrawable(res, R.drawable.baseline_music_off_white_36dp, null);
        notMuted = ResourcesCompat.getDrawable(res, R.drawable.baseline_music_note_white_36dp, null);

        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.welcome_logo_anim);
        imgGameLogo.startAnimation(animShake);

        onReturnCheckNewPlayer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && (requestCode == 0 || requestCode == 1)) {
            onReturnCheckNewPlayer();
        }
    }

    protected void onReturnCheckNewPlayer() {
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

    public void onClickAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void onClickQuit(View view) {
        myBGM.stop();
        this.finishAffinity();
    }

    private void setupWindowAnimations() {
        Explode explode = new Explode();
        explode.setDuration(3000);
        getWindow().setExitTransition(explode);
    }

    public void onClickMuteSwitch(View view) {
        if (myBGM.isPlaying()){
            myBGM.pause();
            muteToggle.setImageDrawable(Muted);
        }else {
            myBGM.start();
            muteToggle.setImageDrawable(notMuted);
        }

    }
}
