package com.exercise.android.morra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;

public class RecordActivity extends AppCompatActivity {
    TableLayout tbData;
    LinearLayout lineLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_record);

        tbData = findViewById(R.id.tbData);
        lineLayout = findViewById(R.id.lineLayout);

        fillTable(getGamesLog());
        int[] winLostCount = getWinLostCount();

        BarChartView barChartView = new BarChartView(this, winLostCount[0], winLostCount[1]);
        lineLayout.addView(barChartView);
    }

    public Cursor getGamesLog() {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.exercise.android.morra/morraDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("SELECT * FROM GamesLog", null);
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return cursor;
    }

    public int[] getWinLostCount() {
        int winCount = 0, lostCount = 0;
        Cursor cursor = null;
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.exercise.android.morra/morraDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("SELECT COUNT(*) FROM GamesLog WHERE winOrLost = 'Win'", null);
            cursor.moveToFirst();
            winCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("COUNT(*)")));

            cursor = db.rawQuery("SELECT COUNT(*) FROM GamesLog WHERE winOrLost = 'Lost'", null);
            cursor.moveToFirst();
            lostCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("COUNT(*)")));
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
//        Toast.makeText(this, winCount + ", " + lostCount, Toast.LENGTH_LONG).show();
        return new int[]{winCount, lostCount};
    }

    public void fillTable(Cursor cursor) throws SQLiteException {
        tbData.removeAllViews();
        fillInfo(true, "Date", "Time", "Opponent Name", "Result");
        while (cursor.moveToNext()) {
            fillInfo(false, cursor.getString(cursor.getColumnIndex("gameDate")), cursor.getString(cursor.getColumnIndex("gameTime")), cursor.getString(cursor.getColumnIndex("opponentName")), cursor.getString(cursor.getColumnIndex("winOrLost")));
        }
    }

    public void fillInfo(boolean header, String gameDate, String gameTime, String opponentName, String winOrLost) {
        TableRow tr = new TableRow(this);
        if (header) {
        }
        tr.setBackgroundColor(0xFD361C1D);

        int textColor = 0xFF9E8877;
        int winTextColor = 0xFF4CAF50;
        int lostTextColor = 0xFFD81E3A;
        int textSize = 20;

        TextView tvDate = new TextView(this);
        tvDate.setText(gameDate);
        tvDate.setTextSize(textSize);
        tvDate.setTextColor(textColor);
        tvDate.setPadding(60, 10, 30, 10);

        TextView tvTime = new TextView(this);
        tvTime.setText(gameTime);
        tvTime.setTextSize(textSize);
        tvTime.setTextColor(textColor);
        tvTime.setPadding(30, 10, 30, 10);

        TextView tvName = new TextView(this);
        tvName.setText(opponentName);
        tvName.setTextSize(textSize);
        tvName.setTextColor(textColor);
        tvName.setPadding(60, 10, 30, 10);

        TextView tvResult = new TextView(this);
        tvResult.setText(winOrLost);
        tvResult.setTextSize(textSize);
        tvResult.setTextColor(textColor);
        if (winOrLost.equals("Win")) {
            tvResult.setTextColor(winTextColor);
        } else if (winOrLost.equals("Lost")) {
            tvResult.setTextColor(lostTextColor);
        }
        tvResult.setPadding(60, 10, 30, 10);

        tr.addView(tvDate);
        tr.addView(tvTime);
        tr.addView(tvName);
        tr.addView(tvResult);
        tbData.addView(tr);
    }

    class BarChartView extends View {
        Paint paint;
        String title = "Game Record";
        int winCount, lostCount, maxValue, yAxisLength, xAxisLength;
        int[] maxGridCount, yAxisStart, xAxisStart;
        float interval;

        public BarChartView(Context context, int winCount, int lostCount) {
            super(context);
            paint = new Paint();
            this.winCount = winCount;
            this.lostCount = lostCount;
            yAxisLength = 1000;
            xAxisLength = 1000;
            yAxisStart = new int[]{230, 300};
            xAxisStart = new int[]{yAxisStart[0], yAxisStart[1] + yAxisLength};
            maxValue = winCount > lostCount ? winCount : lostCount;
            if (maxValue <= 25) {
                maxGridCount = new int[]{0, 5, 10, 15, 20, 25};
            } else {
                int multi = maxValue / 25 + (maxValue % 25 == 0 ? 0 : 1);
                maxGridCount = new int[]{0, 5 * multi, 10 * multi, 15 * multi, 20 * multi, 25 * multi};
            }
            interval = yAxisLength / 5f;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw the title
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(70);
            canvas.drawText(title, 520, 150, paint);

            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(200, 250, 1260, 1400, paint);

            paint.setStrokeWidth(3);
            canvas.drawLine(yAxisStart[0], yAxisStart[1], yAxisStart[0], yAxisStart[1] + yAxisLength, paint);
            canvas.drawLine(xAxisStart[0], xAxisStart[1], xAxisStart[0] + xAxisLength, xAxisStart[1], paint);

            float x = yAxisStart[0], y = yAxisStart[1] + yAxisLength;
            for (int i = 0; i < 6; i++) {
                canvas.drawLine(x - 30, y, x, y, paint);
                canvas.drawText(String.valueOf(maxGridCount[i]), x - 130, y + 10, paint);
                y -= interval;
            }
        }
    }
}