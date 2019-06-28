package com.exercise.android.lab05_q2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends Activity {
    private long startTime = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        startTime = System.currentTimeMillis();
        TextView tvName = (TextView) findViewById(R.id.TVname);
        TextView tvTrial = (TextView) findViewById(R.id.TVtrial);
        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));
        tvTrial.setText(intent.getStringExtra("trial"));
    }

    public void onFinish(View view) {
        finish();
    }

    @Override
    public void finish() {
        long finishTime = System.currentTimeMillis();
        int elapsedTime = (int) (finishTime-startTime)/1000;
        Intent result = new Intent();
        result.putExtra("playTime", elapsedTime+"");
        setResult(RESULT_OK, result);
        super.finish();
    }
}

