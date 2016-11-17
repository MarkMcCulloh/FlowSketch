package cornjob.flowsketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

public class ShapeCircle extends Object {

    private float circleRadius;

    ShapeCircle(float x, float y, float r) {
        objOrigin = new Point(x, y);
        circleRadius = r;

        objPaint = new Paint();

        objPaint = new Paint();
        objPaint.setColor(Color.BLACK);
        objPaint.setStyle(Paint.Style.STROKE);
        objPaint.setStrokeWidth(10f);
    }

    @Override
    public boolean drawThis(Canvas objCanvas) {
        objCanvas.drawCircle(objOrigin.getX(), objOrigin.getY(), circleRadius, objPaint);
        return true;
    }

    @Override
    public boolean contains(Point test) {
        if (Point.distance(objOrigin, test) <= circleRadius * MyCanvas.mScaleFactor) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    @Override
    public void rotate(float angle) {
        //circle lol
    }

    @Override
    public void scale(float factor) {
        circleRadius *= factor;
    }


    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }
}

