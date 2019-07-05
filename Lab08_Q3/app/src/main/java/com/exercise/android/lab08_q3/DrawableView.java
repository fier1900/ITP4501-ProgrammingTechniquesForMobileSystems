package com.exercise.android.lab08_q3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawableView extends View {
    Paint paint = new Paint();
    double gCenterX, gCenterY, gRadius;

    public DrawableView(Context context) {
        super(context);
        paint.setAntiAlias(true);
        paint.setColor(0xff00ffff);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /** COMPLETE THIS PART **/
        // Draw a circle with center (gCenterX, gCenterY) and radius ‘gRadius’.
        for (int i = (int) gRadius; i > 0; i -= 30) {
            canvas.drawCircle((float) gCenterX, (float) gCenterY, (float) i, paint);
        }

        invalidate();

        // Extension: Draw a set of concentric circles.
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();  // no. of touch points

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (pointerCount == 2)  // Multi-touch
                {
                    /** COMPLETE THIS PART **/
                    // Get the co-ordinates of the points of touch
                    double x1 = event.getX(0);
                    double y1 = event.getY(0);
                    double x2 = event.getX(1);
                    double y2 = event.getY(1);
                    // Find the center (mid-point of the points of touch)
                    gCenterX = (x1 + x2) / 2;
                    gCenterY = (y1 + y2) / 2;
                    // Find the radius of the circle (Hint: use the Pythagoras' theorem,
                    // and use Math.sqrt to find the square root)
                    gRadius = (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))) / 2;
                    Toast.makeText(getContext(), String.valueOf(gRadius), Toast.LENGTH_SHORT).show();
                }

                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }
}
