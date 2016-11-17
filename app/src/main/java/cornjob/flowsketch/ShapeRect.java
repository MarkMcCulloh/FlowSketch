package cornjob.flowsketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.RectF;

public class ShapeRect extends Object {

    private RectF fullRect;

    ShapeRect(float x, float y, float l, float w) {
        objOrigin = new Point(x, y);
        fullRect = new RectF(x, y, l, w);

        objPaint = new Paint();

        objPaint = new Paint();
        objPaint.setColor(Color.BLACK);
        objPaint.setStyle(Paint.Style.STROKE);
        objPaint.setStrokeWidth(10f);
    }

    ShapeRect(float x, float y, float s) {
        objOrigin = new Point(x, y);
        fullRect = new RectF(x, y, x + s, y + s);

        objPaint = new Paint();

        objPaint = new Paint();
        objPaint.setColor(Color.BLACK);
        objPaint.setStyle(Paint.Style.STROKE);
        objPaint.setStrokeWidth(10f);
    }

    @Override
    public boolean drawThis(Canvas objCanvas) {
        objCanvas.drawRect(fullRect, objPaint);
        return true;
    }

    @Override
    public boolean contains(Point test) {
        return false;
    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void scale(float factor) {

    }
}

