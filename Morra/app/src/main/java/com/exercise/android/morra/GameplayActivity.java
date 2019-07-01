package com.exercise.android.morra;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GameplayActivity extends AppCompatActivity implements View.OnTouchListener {
    LinearLayout layout;
    TextView tvOpponentName, tvOpponentFlag;
    ImageView imgOpponentFlag;
    String urlPre, opponentName, opponentCountry;
    int playerLeft, playerRight, opponentID, opponentLeft, opponentRight, opponentGuess, bitmapWidth, bitmapHeight;
    int[][] handsPositon;
    boolean playerHandLeftToggle, playerHandRightToggle;
    Bitmap playerHandLeftStone, playerHandLeftPaper, playerHandRightStone, playerHandRightPaper, opponentHandLeftStone, opponentHandRightStone, opponentHandLeftPaper, opponentHandRightPaper;

    class GameplayView extends View {
        Paint paint;

        public GameplayView(Context context) {
            super(context);
            paint = new Paint();
        }

        public void onDraw(Canvas canvas) {
            paint.setAntiAlias(true);
            paint.setTextSize(100);

            if (opponentLeft == 0) {
                canvas.drawBitmap(opponentHandLeftStone, handsPositon[1][0], handsPositon[1][1], null);
            } else {
                canvas.drawBitmap(opponentHandLeftPaper, handsPositon[1][0], handsPositon[1][1], null);
            }
            if (opponentRight == 0) {
                canvas.drawBitmap(opponentHandRightStone, handsPositon[0][0], handsPositon[0][1], null);
            } else {
                canvas.drawBitmap(opponentHandRightPaper, handsPositon[0][0], handsPositon[0][1], null);
            }
            if (playerHandLeftToggle) {
                playerLeft = 5;
                canvas.drawBitmap(playerHandLeftPaper, handsPositon[2][0], handsPositon[2][1], null);
            } else {
                playerLeft = 0;
                canvas.drawBitmap(playerHandLeftStone, handsPositon[2][0], handsPositon[2][1], null);
            }
            if (playerHandRightToggle) {
                playerRight = 5;
                canvas.drawBitmap(playerHandRightPaper, handsPositon[3][0], handsPositon[3][1], null);
            } else {
                playerRight = 0;
                canvas.drawBitmap(playerHandRightStone, handsPositon[3][0], handsPositon[3][1], null);
            }

            if (checkGuess()){
                paint.setColor(Color.GREEN);
            }else {
                paint.setColor(Color.RED);
            }
            canvas.drawText(String.valueOf(opponentGuess), 700, 1100, paint);
            invalidate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_gameplay);

        layout = findViewById(R.id.linear);
        tvOpponentName = findViewById(R.id.tvOpponentName);
        tvOpponentFlag = findViewById(R.id.tvOpponentFlag);
        imgOpponentFlag = findViewById(R.id.imgOpponentFlag);

        playerHandLeftStone = BitmapFactory.decodeResource(getResources(), R.drawable.player_left_stone);
        playerHandRightStone = BitmapFactory.decodeResource(getResources(), R.drawable.player_right_stone);
        playerHandLeftPaper = BitmapFactory.decodeResource(getResources(), R.drawable.player_left_paper);
        playerHandRightPaper = BitmapFactory.decodeResource(getResources(), R.drawable.player_right_paper);

        opponentHandLeftStone = BitmapFactory.decodeResource(getResources(), R.drawable.oppo_left_stone);
        opponentHandRightStone = BitmapFactory.decodeResource(getResources(), R.drawable.oppo_right_stone);
        opponentHandLeftPaper = BitmapFactory.decodeResource(getResources(), R.drawable.oppo_left_paper);
        opponentHandRightPaper = BitmapFactory.decodeResource(getResources(), R.drawable.oppo_right_paper);

        bitmapWidth = 500;
        bitmapHeight = 500;

        playerHandLeftToggle = false;
        playerHandRightToggle = false;

        handsPositon = new int[][]{{140, 200}, {850, 200}, {140, 1600}, {850, 1600}};

        urlPre = "https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/";

        GetOpponentInfoTask task = new GetOpponentInfoTask();
        task.execute(urlPre + "0");

        GameplayView gameplayView = new GameplayView(this);
        layout.addView(gameplayView);
        gameplayView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Check if the x and y position of the touch is inside the bitmap
            if (x > handsPositon[2][0] && x < handsPositon[2][0] + bitmapWidth && y > handsPositon[2][1] && y < handsPositon[2][1] + bitmapHeight) {
                //Bitmap touched
                playerHandLeftToggle = !playerHandLeftToggle;
            } else if (x > handsPositon[3][0] && x < handsPositon[3][0] + bitmapWidth && y > handsPositon[3][1] && y < handsPositon[3][1] + bitmapHeight) {
                //Bitmap touched
                playerHandRightToggle = !playerHandRightToggle;
            }
        }
        return true;
    }

    protected boolean checkGuess() {
        return playerLeft + playerRight + opponentLeft + opponentRight == opponentGuess;
    }

    protected String getJson(String value) {
        InputStream inputStream;
        String result = "";
        URL url;

        try {
            url = new URL(value);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.connect();

            inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void updateOpponentFlag(String country) {
        Resources res = getResources();
        Drawable drawable;
        switch (country) {
            case "CN":
                drawable = ResourcesCompat.getDrawable(res, R.drawable.flag_cn, null);
                break;
            case "HK":
                drawable = ResourcesCompat.getDrawable(res, R.drawable.flag_hk, null);
                break;
            case "JP":
                drawable = ResourcesCompat.getDrawable(res, R.drawable.flag_jp, null);
                break;
            case "UK":
                drawable = ResourcesCompat.getDrawable(res, R.drawable.flag_uk, null);
                break;
            case "US":
                drawable = ResourcesCompat.getDrawable(res, R.drawable.flag_us, null);
                break;
            default:
                drawable = ResourcesCompat.getDrawable(res, R.drawable.flag_hk, null);
                break;
        }
        imgOpponentFlag.setImageDrawable(drawable);
    }

    private class GetOpponentHandsTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... values) {
            return getJson(values[0]); //return to onPostExecute
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                opponentLeft = jsonObject.getInt("left");
                opponentRight = jsonObject.getInt("right");
                opponentGuess = jsonObject.getInt("guess");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetOpponentInfoTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... values) {
            return getJson(values[0]); //return to onPostExecute
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                opponentID = jsonObject.getInt("id");
                opponentName = jsonObject.getString("name");
                opponentCountry = jsonObject.getString("country");

                tvOpponentName.setText(opponentName);
                tvOpponentFlag.setText(opponentCountry);
                updateOpponentFlag(opponentCountry);

                GetOpponentHandsTask task = new GetOpponentHandsTask();
                task.execute(urlPre + opponentID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

