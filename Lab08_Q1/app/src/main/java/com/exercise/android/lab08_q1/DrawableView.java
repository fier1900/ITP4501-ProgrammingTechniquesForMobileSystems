package com.exercise.android.lab08_q1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by admin on 14/5/15.
 */
public class DrawableView extends View {

    float x1, y1; // first corner
    float x2, y2; // second corner

    Paint paint = new Paint();

    public DrawableView(Context context) {
        super(context);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** COMPLETE THIS PART **/
        // Draw a rectangle of opposite corners (x1, y1) and (x2, y2) with property & paint
        canvas.drawRect(x1, y1, x2, y2, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            paint.setColor(Color.YELLOW);
            x1 = event.getX();
            y1 = event.getY();
            x2 = event.getX();
            y2 = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            /** COMPLETE THIS PART **/
            // update x2 and y2
            x2 = event.getX();
            y2 = event.getY();

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            /** COMPLETE THIS PART **/
            // update x2 and y2
            // set the paint color to blue
            paint.setColor(Color.BLUE);
            x2 = event.getX();
            y2 = event.getY();

        }
        Log.d("Event", "point: " + x1 + " " + y1 + " " + x2 + " " + y2);
        invalidate();
        return true;
    }
}
