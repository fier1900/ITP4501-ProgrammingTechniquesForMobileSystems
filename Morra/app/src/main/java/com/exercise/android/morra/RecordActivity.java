package com.exercise.android.morra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

public class RecordActivity extends AppCompatActivity {
    TableLayout tbData;
    LinearLayout lineLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record);

        tbData = findViewById(R.id.tbData);
        lineLayout = findViewById(R.id.lineLayout);

        fillGameRecordTable(getGamesLog());
        int[] winLostCount = getWinLostCount();

        BarChartView barChartView = new BarChartView(this, winLostCount[0], winLostCount[1]);
        lineLayout.addView(barChartView);
    }

    // retrieve database record
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

    // count the number of win and lost
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
        //Toast.makeText(this, winCount + ", " + lostCount, Toast.LENGTH_LONG).show();
        return new int[]{winCount, lostCount};
    }

    // fill in record table
    public void fillGameRecordTable(Cursor cursor) throws SQLiteException {
        tbData.removeAllViews();
        fillGameRecordInfo(true, "Date", "Time", "Opponent", "Country", "Result");
        while (cursor.moveToNext()) {
            fillGameRecordInfo(false, cursor.getString(cursor.getColumnIndex("gameDate")), cursor.getString(cursor.getColumnIndex("gameTime")), cursor.getString(cursor.getColumnIndex("opponentName")), cursor.getString(cursor.getColumnIndex("country")), cursor.getString(cursor.getColumnIndex("winOrLost")));
        }
    }

    // fill in each row in record table
    public void fillGameRecordInfo(boolean header, String gameDate, String gameTime, String opponentName, String country, String winOrLost) {
        TableRow tr = new TableRow(this);

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
        tvTime.setPadding(20, 10, 30, 10);

        TextView tvName = new TextView(this);
        tvName.setText(opponentName);
        tvName.setTextSize(textSize);
        tvName.setTextColor(textColor);
        tvName.setPadding(50, 10, 30, 10);

        TextView tvCountry = new TextView(this);
        tvCountry.setText(country);
        tvCountry.setTextSize(textSize);
        tvCountry.setTextColor(textColor);
        tvCountry.setPadding(25, 10, 30, 10);

        TextView tvResult = new TextView(this);
        tvResult.setText(winOrLost);
        tvResult.setTextSize(textSize);
        tvResult.setTextColor(textColor);
        if (winOrLost.equals("Win")) {
            tvResult.setTextColor(winTextColor);
        } else if (winOrLost.equals("Lost")) {
            tvResult.setTextColor(lostTextColor);
        }
        tvResult.setPadding(25, 10, 30, 10);

        tr.addView(tvDate);
        tr.addView(tvTime);
        tr.addView(tvName);
        tr.addView(tvCountry);
        tr.addView(tvResult);
        tbData.addView(tr);
    }

    class BarChartView extends View {
        Paint paint;
        String title = "Game Record";
        int winCount, lostCount, maxValue, yAxisLength, xAxisLength;
        int[] maxGridCount, yAxisStart, xAxisStart;
        float interval, winBarLength, lostBarLength;

        public BarChartView(Context context, int winCount, int lostCount) {
            super(context);
            paint = new Paint();
            this.winCount = winCount;
            this.lostCount = lostCount;
            yAxisLength = 1000;
            xAxisLength = 1000;
            interval = yAxisLength / 5f;
            yAxisStart = new int[]{270, 300};
            xAxisStart = new int[]{yAxisStart[0], yAxisStart[1] + yAxisLength};
            maxValue = winCount > lostCount ? winCount : lostCount;
            if (maxValue <= 10) {
                maxGridCount = new int[]{0, 2, 4, 6, 8, 10};
            } else if (maxValue <= 25) {
                maxGridCount = new int[]{0, 5, 10, 15, 20, 25};
            } else {
                int multi = maxValue / 25 + (maxValue % 25 == 0 ? 0 : 1);
                maxGridCount = new int[]{0, 5 * multi, 10 * multi, 15 * multi, 20 * multi, 25 * multi};
            }
            winBarLength = (float) winCount / maxGridCount[maxGridCount.length - 1] * yAxisLength;
            lostBarLength = (float) lostCount / maxGridCount[maxGridCount.length - 1] * yAxisLength;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // draw the title
            paint.setColor(0xFFE0DAD5);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(80);
            canvas.drawText(title, 500, 170, paint);

//            canvas.drawRect(200, 250, 1260, 1400, paint);

            // draw the x and y axis
            paint.setTextSize(60);
            paint.setStrokeWidth(3);
            canvas.drawLine(yAxisStart[0], yAxisStart[1], yAxisStart[0], yAxisStart[1] + yAxisLength, paint);
            canvas.drawLine(xAxisStart[0], xAxisStart[1], xAxisStart[0] + xAxisLength, xAxisStart[1], paint);

            // draw the x axis marker
            float x = yAxisStart[0], y = yAxisStart[1] + yAxisLength;
            for (int i = 0; i < 6; i++) {
                canvas.drawLine(x - 35, y, x, y, paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(maxGridCount[i]), x - 60, y + 10, paint);
                y -= interval;
            }

            // draw the y axis marker
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Win Count", yAxisStart[0] + 150, yAxisStart[1] + yAxisLength + 100, paint);
            canvas.drawText("Lost Count", yAxisStart[0] + 550, yAxisStart[1] + yAxisLength + 100, paint);

            // draw the win and lost count bar
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0xFF4CAF50);
            canvas.drawRect(yAxisStart[0] + 250, yAxisStart[1] + yAxisLength - winBarLength, yAxisStart[0] + 250 + 100, yAxisStart[1] + yAxisLength - 2, paint);

            paint.setColor(0xFFD81E3A);
            canvas.drawRect(yAxisStart[0] + 650, yAxisStart[1] + yAxisLength - lostBarLength, yAxisStart[0] + 650 + 100, yAxisStart[1] + yAxisLength - 2, paint);
        }
    }
}