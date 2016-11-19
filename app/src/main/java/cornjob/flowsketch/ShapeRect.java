package cornjob.flowsketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.RectF;

public class ShapeRect extends Object {

    private RectF fullRect;

    ShapeRect(MyCanvas mainCanvas, float x, float y, float l, float w) {
        super(mainCanvas, x, y, OBJTYPE.RECTANGLE);

        fullRect = new RectF(x, y, l, w);

        objPaintRegular = new Paint();
        objPaintRegular.setColor(Color.BLACK);
        objPaintRegular.setStyle(Paint.Style.STROKE);
        objPaintRegular.setStrokeWidth(10f);

        objPaintSelected = new Paint();
        objPaintSelected.setColor(Color.YELLOW);
        objPaintSelected.setStyle(Paint.Style.STROKE);
        objPaintSelected.setStrokeWidth(10f);

        objPaintCurrent = objPaintRegular;
    }

    ShapeRect(MyCanvas mainCanvas, float x, float y, float s) {
        super(mainCanvas, x, y, OBJTYPE.SQUARE);

        fullRect = new RectF(x, y, x + s, y + s);

        objPaintRegular = new Paint();
        objPaintRegular.setColor(Color.BLACK);
        objPaintRegular.setStyle(Paint.Style.STROKE);
        objPaintRegular.setStrokeWidth(10f);

        objPaintSelected = new Paint();
        objPaintSelected.setColor(Color.YELLOW);
        objPaintSelected.setStyle(Paint.Style.STROKE);
        objPaintSelected.setStrokeWidth(10f);

        objPaintCurrent = objPaintRegular;
    }

    @Override
    public boolean drawThis() {
        objCanvas.canvas.drawRect(fullRect, objPaintCurrent);
        return true;
    }

    @Override
    public boolean contains(Point test) {
        return fullRect.contains(test.getX(), test.getY());

    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
        fullRect.offset(xdis, ydis);
    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void scale(float factor) {

    }
}

