package com.exercise.android.lab07_q2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private LinearLayout layout;
    private boolean alarmOff = true;

    class AlarmView extends View {
        private boolean isFlash = false;

        public AlarmView(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas) {

            canvas.drawColor(Color.BLACK);
            Paint paint = new Paint();
            paint.setAntiAlias(true);

            paint.setColor(Color.WHITE);
            canvas.drawCircle(getWidth() / 2, getHeight()/2, 320, paint);

            // Set the lamp in Yellow Colour
            if (alarmOff) {
                paint.setColor(Color.YELLOW);
            } else if (!isFlash) {
                isFlash = true;
                // Set the lamp in Red colour
                paint.setColor(Color.RED);

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    //no nothing
                }
            } else {
                // Set the lamp in Black colour
                paint.setColor(Color.BLACK);
                isFlash = false;
            }

            // Draw the lamp
            canvas.drawCircle(getWidth() / 2, getHeight()/2, 300, paint);
            invalidate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button1);
        layout = (LinearLayout) findViewById(R.id.linearLayout1);

        AlarmView alarmView = new AlarmView(this);
        layout.addView(alarmView);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alarmOff = ! alarmOff;
            }
        });
    }
}
