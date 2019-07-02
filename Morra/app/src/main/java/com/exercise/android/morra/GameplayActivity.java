package com.exercise.android.morra;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
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

public class GameplayActivity extends AppCompatActivity {
    LinearLayout layout;
    TextView tvOpponentName, tvOpponentFlag;
    ImageView imgOpponentFlag;
    String urlPre, opponentName, opponentCountry;
    int playerLeft, playerRight, playerGuess, opponentID, opponentLeft, opponentRight, opponentGuess, bitmapWidth, bitmapHeight, playerWinCount, oppWinCount;
    int[][] handsPosition;
    int[] msgPosition, guessPosition;
    GestureDetector gestureDetector;
    OnGestureListener gestureListener;
    CountDownTimer countDownTimer;
    boolean playerHandLeftToggle, playerHandRightToggle, isPlayerTurn, isPlayerGuessing, isPlayerWinTurn, isPlayerLoseTurn, isOpponentTurn, isOpponentGuessing, isOpponentWinTurn, isOpponentLoseTurn, isGameWin, isGameLose, showOppoonentHands, isGameFinished;
    Bitmap playerHandLeftStone, playerHandLeftPaper, playerHandRightStone, playerHandRightPaper, opponentHandLeftStone, opponentHandRightStone, opponentHandLeftPaper, opponentHandRightPaper, yourGuess, yourTurn, makeMove, oppoTurn, playerGuessed, oppoGuessed, guessedWrong, guess0, guess5, guess10, guess15, guess20, gameWin, gameLose, playAgain, sure, backToMenu;

    class GameplayView extends View {
        Paint paint;

        public GameplayView(Context context) {
            super(context);
            paint = new Paint();
        }

        public void onDraw(Canvas canvas) {
            paint.setAntiAlias(true);
            paint.setTextSize(100);

            if (showOppoonentHands) {
                if (opponentLeft == 0) {
                    canvas.drawBitmap(opponentHandLeftStone, handsPosition[1][0], handsPosition[1][1], null);
                } else {
                    canvas.drawBitmap(opponentHandLeftPaper, handsPosition[1][0], handsPosition[1][1], null);
                }
                if (opponentRight == 0) {
                    canvas.drawBitmap(opponentHandRightStone, handsPosition[0][0], handsPosition[0][1], null);
                } else {
                    canvas.drawBitmap(opponentHandRightPaper, handsPosition[0][0], handsPosition[0][1], null);
                }
            } else {
                canvas.drawBitmap(opponentHandLeftStone, handsPosition[1][0], handsPosition[1][1], null);
                canvas.drawBitmap(opponentHandRightStone, handsPosition[0][0], handsPosition[0][1], null);
            }

            if (playerHandLeftToggle) {
                playerLeft = 5;
                canvas.drawBitmap(playerHandLeftPaper, handsPosition[2][0], handsPosition[2][1], null);
            } else {
                playerLeft = 0;
                canvas.drawBitmap(playerHandLeftStone, handsPosition[2][0], handsPosition[2][1], null);
            }
            if (playerHandRightToggle) {
                playerRight = 5;
                canvas.drawBitmap(playerHandRightPaper, handsPosition[3][0], handsPosition[3][1], null);
            } else {
                playerRight = 0;
                canvas.drawBitmap(playerHandRightStone, handsPosition[3][0], handsPosition[3][1], null);
            }

            if (isPlayerLoseTurn || isOpponentLoseTurn) {
                canvas.drawBitmap(guessedWrong, msgPosition[0] - 170, msgPosition[1], null);
            }
            if (isPlayerWinTurn) {
                canvas.drawBitmap(playerGuessed, msgPosition[0] - 70, msgPosition[1], null);
            }

            if (isPlayerTurn) {
                canvas.drawBitmap(yourTurn, msgPosition[0] + 50, msgPosition[1], null);
            }

            if (isOpponentWinTurn) {
                canvas.drawBitmap(oppoGuessed, msgPosition[0] - 160, msgPosition[1], null);
            }
            if (isOpponentTurn) {
                canvas.drawBitmap(oppoTurn, msgPosition[0] - 160, msgPosition[1], null);
            }

            if (isOpponentGuessing) {
                canvas.drawBitmap(makeMove, msgPosition[0] - 160, msgPosition[1], null);
            }

            if (isPlayerGuessing) {
                canvas.drawBitmap(yourGuess, msgPosition[0], msgPosition[1], null);
                switch (playerGuess) {
                    case 0:
                        canvas.drawBitmap(guess0, guessPosition[0], guessPosition[1], null);
                        break;
                    case 5:
                        canvas.drawBitmap(guess5, guessPosition[0], guessPosition[1], null);
                        break;
                    case 10:
                        canvas.drawBitmap(guess10, guessPosition[0], guessPosition[1], null);
                        break;
                    case 15:
                        canvas.drawBitmap(guess15, guessPosition[0], guessPosition[1], null);
                        break;
                    case 20:
                        canvas.drawBitmap(guess20, guessPosition[0], guessPosition[1], null);
                        break;
                }
            }

            if (isGameWin) {
                canvas.drawBitmap(gameWin, msgPosition[0] + 120, msgPosition[1], null);
            }
            if (isGameLose) {
                canvas.drawBitmap(gameLose, msgPosition[0] + 50, msgPosition[1], null);
            }

            if (isGameFinished) {
                canvas.drawBitmap(playAgain, msgPosition[0] + 10, msgPosition[1], null);
                canvas.drawBitmap(backToMenu, msgPosition[0] + 50, msgPosition[1] + 320, null);
                canvas.drawBitmap(sure, msgPosition[0] + 700, msgPosition[1] + 300, null);

            }
            canvas.drawText("G" + opponentGuess + ", L" + opponentLeft + ", R" + opponentRight, 500, 700, paint);
            invalidate();
        }
    }

    class OnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            float startX = e.getX();
            float startY = e.getY();
            if (isGameFinished) {
                if ((startY >= 1500 && startY <= 1800) && (startX >= 200 && startX <= 500)) {
//                    Toast.makeText(getApplicationContext(), "return", Toast.LENGTH_SHORT).show();
                    finish();
                } else if ((startY >= 1500 && startY <= 1800) && (startX >= 900 && startX <= 1200)) {
//                    Toast.makeText(getApplicationContext(), "sure", Toast.LENGTH_SHORT).show();
                    onNewGame();
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float startX = e1.getX();
            float startY = e1.getY();
            float endX = e2.getX();
            float endY = e2.getY();

            if (distanceY <= 50 && distanceY >= -50 && startY >= 1200 && startY <= 2100) {
                if (distanceX >= 100) {
//                    Toast.makeText(getApplicationContext(), "swipe left", Toast.LENGTH_SHORT).show();
                    if (playerGuess < 20) {
                        playerGuess += 5;
                        return true;
                    }
                } else if (distanceX <= -100) {
//                    Toast.makeText(getApplicationContext(), "swipe right", Toast.LENGTH_SHORT).show();
                    if (playerGuess > 0) {
                        playerGuess -= 5;
                        return true;
                    }
                }
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
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

        yourGuess = BitmapFactory.decodeResource(getResources(), R.drawable.your_guess);
        yourTurn = BitmapFactory.decodeResource(getResources(), R.drawable.your_turn);
        oppoTurn = BitmapFactory.decodeResource(getResources(), R.drawable.oppe_turn);
        playerGuessed = BitmapFactory.decodeResource(getResources(), R.drawable.player_guessed);
        oppoGuessed = BitmapFactory.decodeResource(getResources(), R.drawable.oppo_guessed);
        guessedWrong = BitmapFactory.decodeResource(getResources(), R.drawable.guessed_wrong);
        guess0 = BitmapFactory.decodeResource(getResources(), R.drawable.guess_0);
        guess5 = BitmapFactory.decodeResource(getResources(), R.drawable.guess_5);
        guess10 = BitmapFactory.decodeResource(getResources(), R.drawable.guess_10);
        guess15 = BitmapFactory.decodeResource(getResources(), R.drawable.guess_15);
        guess20 = BitmapFactory.decodeResource(getResources(), R.drawable.guess_20);
        gameWin = BitmapFactory.decodeResource(getResources(), R.drawable.you_win);
        gameLose = BitmapFactory.decodeResource(getResources(), R.drawable.you_lose);
        makeMove = BitmapFactory.decodeResource(getResources(), R.drawable.player_move);
        playAgain = BitmapFactory.decodeResource(getResources(), R.drawable.play_again);
        sure = BitmapFactory.decodeResource(getResources(), R.drawable.sure);
        backToMenu = BitmapFactory.decodeResource(getResources(), R.drawable.back);

        handsPosition = new int[][]{{140, 200}, {850, 200}, {140, 2000}, {850, 2000}};
        msgPosition = new int[]{200, 900};
        guessPosition = new int[]{600, 1150};

        onNewGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        gestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Check if the x and y position of the touch is inside the bitmap
            if (x > handsPosition[2][0] && x < handsPosition[2][0] + bitmapWidth && y > handsPosition[2][1] + 300 && y < handsPosition[2][1] + bitmapHeight) {
                //Bitmap touched
                playerHandLeftToggle = !playerHandLeftToggle;
            } else if (x > handsPosition[3][0] && x < handsPosition[3][0] + bitmapWidth && y > handsPosition[3][1] + 300 && y < handsPosition[3][1] + bitmapHeight) {
                //Bitmap touched
                playerHandRightToggle = !playerHandRightToggle;
            }
        }
        return true;
    }

    protected void checkPlayerGuess() {
        showOppoonentHands = true;
        if (playerLeft + playerRight + opponentLeft + opponentRight == playerGuess) {
            playerWinCount += 1;
            if (playerWinCount >= 2) {
                isPlayerWinTurn = true;

                countDownTimer = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        showOppoonentHands = false;
                        playerHandLeftToggle = false;
                        playerHandRightToggle = false;
                        isPlayerWinTurn = false;
                        isGameWin = true;
                        onGameFinished();
                    }
                };
                countDownTimer.start();
            } else {
                isPlayerWinTurn = true;
                countDownTimer = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        isPlayerWinTurn = false;
                        showOppoonentHands = false;
                        playerHandLeftToggle = false;
                        playerHandRightToggle = false;
                        GetOpponentHandsTask task = new GetOpponentHandsTask();
                        task.execute(urlPre + opponentID);
                        goPlayerTurn();
                    }
                };
                countDownTimer.start();
            }
        } else {
            playerWinCount = 0;
            isPlayerLoseTurn = true;
            countDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    isPlayerLoseTurn = false;
                    showOppoonentHands = false;
                    playerHandLeftToggle = false;
                    playerHandRightToggle = false;
                    GetOpponentHandsTask task = new GetOpponentHandsTask();
                    task.execute(urlPre + opponentID);
                    goOpponentTurn();
                }
            };
            countDownTimer.start();
        }
    }

    protected void checkOpponentGuess() {
        showOppoonentHands = true;
        if (playerLeft + playerRight + opponentLeft + opponentRight == opponentGuess) {
            oppWinCount += 1;
            if (oppWinCount >= 2) {
                isOpponentWinTurn = true;
                countDownTimer = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        isOpponentWinTurn = false;
                        isGameLose = true;
                        showOppoonentHands = false;
                        playerHandLeftToggle = false;
                        playerHandRightToggle = false;
                        onGameFinished();
                    }
                };
                countDownTimer.start();
            } else {
                isOpponentWinTurn = true;
                countDownTimer = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        isOpponentWinTurn = false;
                        showOppoonentHands = false;
                        playerHandLeftToggle = false;
                        playerHandRightToggle = false;
                        GetOpponentHandsTask task = new GetOpponentHandsTask();
                        task.execute(urlPre + opponentID);
                        goOpponentTurn();
                    }
                };
                countDownTimer.start();
            }
        } else {
            oppWinCount = 0;
            isOpponentLoseTurn = true;
            countDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    isOpponentLoseTurn = false;
                    showOppoonentHands = false;
                    playerHandLeftToggle = false;
                    playerHandRightToggle = false;
                    GetOpponentHandsTask task = new GetOpponentHandsTask();
                    task.execute(urlPre + opponentID);
                    goPlayerTurn();
                }
            };
            countDownTimer.start();
        }
    }

    protected void goPlayerTurn() {
        isPlayerTurn = true;
        playerGuess = 0;
        playerHandLeftToggle = false;
        playerHandRightToggle = false;
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isPlayerTurn = false;
                playerGuess();
            }
        };
        countDownTimer.start();
    }

    protected void goOpponentTurn() {
        isOpponentTurn = true;
        playerGuess = 0;
        playerHandLeftToggle = false;
        playerHandRightToggle = false;
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isOpponentTurn = false;
                playerMoves();
            }
        };
        countDownTimer.start();
    }

    protected void onGameFinished() {
        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isGameWin = false;
                isGameLose = false;
                isGameFinished = true;
            }
        };
        countDownTimer.start();
    }

    protected void onNewGame() {
        bitmapWidth = 650;
        bitmapHeight = 850;
        playerGuess = 0;

        playerHandLeftToggle = false;
        playerHandRightToggle = false;
        isPlayerTurn = true;
        isPlayerGuessing = false;
        isPlayerWinTurn = false;
        isPlayerLoseTurn = false;
        isOpponentGuessing = false;
        isGameWin = false;
        isGameLose = false;
        showOppoonentHands = false;
        isGameFinished = false;

        playerWinCount = 0;
        oppWinCount = 0;

        gestureListener = new OnGestureListener();
        gestureDetector = new GestureDetector(getApplicationContext(), gestureListener);

        urlPre = "https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/";

        GetOpponentInfoTask task = new GetOpponentInfoTask();
        task.execute(urlPre + "0");

        GameplayView gameplayView = new GameplayView(this);
        layout.addView(gameplayView);
        goPlayerTurn();
    }

    protected void playerGuess() {
        isPlayerGuessing = true;
        countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isPlayerGuessing = false;
                checkPlayerGuess();
            }
        };
        countDownTimer.start();
    }

    protected void playerMoves() {
        isOpponentGuessing = true;
        countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isOpponentGuessing = false;
                checkOpponentGuess();
            }
        };
        countDownTimer.start();
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

                GetOpponentHandsTask task = new GetOpponentHandsTask();
                task.execute(urlPre + opponentID);

                tvOpponentName.setText(opponentName);
                tvOpponentFlag.setText(opponentCountry);
                updateOpponentFlag(opponentCountry);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

