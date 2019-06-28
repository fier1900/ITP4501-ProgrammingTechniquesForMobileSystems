package com.exercise.android.lab07_q1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;


public class MainActivity extends AppCompatActivity {

    class Panel extends View {
        public Panel(Context context) {
            super(context);
        }

        String title = "Fund Portfolio";
        String items[] = {"Financials", "Properties", "Utilities", "Others"};
        int data[] = {52, 25, 11, 12};
        int rColor[] = {0xffff0000, 0xffffff00, 0xff32cd32, 0xff880055};
        float cDegree = 0;

        @Override
        public void onDraw(Canvas c) {
            super.onDraw(c);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            // Make the entire canvas in white
            paint.setColor(Color.WHITE);
            c.drawPaint(paint);

            // Draw the pie chart
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            for (int i = 0; i < data.length; i++) {
                float degree = data[i] * 360f / 100f;
                paint.setColor(rColor[i]);

                // define the diameter of the arc
                int diameter = Math.min(this.getWidth(), this.getHeight()) - 100;

                // Draw the arc
                RectF rec = new RectF(200, 300, 1300, 1400);
                c.drawArc(rec, cDegree, degree, true, paint);
                cDegree += degree;
            }

            // Draw the title
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(90);
            paint.setTypeface(Typeface.SERIF);
            c.drawText(title, 50, 100, paint);

            int vertSpace = getHeight() - 100;
            paint.setTextSize(60);

            int top = 1700;


            for (int i = 0; i < data.length; i++) {

                paint.setColor(rColor[i]);
                // Draw the legend rect (20px SQ)
                c.drawRect(900,top,950,top+50,paint);
                top+= 90;

                // Draw the label
                c.drawText(items[i], 970, top-40, paint);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
    }
}
