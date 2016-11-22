package cornjob.flowsketch;

import android.graphics.Color;
import android.graphics.Paint;
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
    public int getColor() {
        return 0;
    }

    @Override
    public float getXPos() {
        return 0;
    }

    @Override
    public float getYPos() {
        return 0;
    }

    @Override
    public float getLength() {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getRadius() {
        return -1;
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
        fullRect.set(objOrigin.getX(),objOrigin.getY(),fullRect.width()+objOrigin.getX(),fullRect.height()+objOrigin.getY());
        //fullRect.offset(xdis, ydis);
    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void scale(float factor) {

    }
    public void setColor(int color,String action){
        objPaintRegular.setColor(color);
        if(action == "Fill") {
            objPaintRegular.setStyle(Paint.Style.FILL);

        }
        else
        {
            objPaintRegular.setStyle(Paint.Style.STROKE);
        }
    }
}

