package com.exercise.android.morra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {
    ImageView imgGameLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);

        imgGameLogo = findViewById(R.id.imgGameLogo);
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.welcome_logo_anim);
        imgGameLogo.startAnimation(animShake);
    }
}
