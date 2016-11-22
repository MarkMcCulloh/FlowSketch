package cornjob.flowsketch;

import android.graphics.Paint;

/**
 * Created by MarkM on 11/17/2016.
 */

public abstract class Object {

    public enum OBJTYPE {
        CIRCLE, LINE, TRIANGLE, RECTANGLE, SQUARE,
        TEXT, IMAGE, LINER
    }

    public MyCanvas objCanvas;
    public Point objOrigin;
    public Paint objPaintCurrent, objPaintSelected, objPaintRegular;
    public float objScale, objRotate, objTranslate;

    public OBJTYPE objType;
    public boolean objSelect;

    Object(MyCanvas mainCanvas, float x, float y, OBJTYPE type) {
        objCanvas = mainCanvas;
        objOrigin = new Point(x, y);
        objType = type;
    }


    public boolean setSelect(boolean flag) {
        boolean last = objSelect;
        objSelect = flag;
        if (flag) {
            objPaintCurrent = objPaintSelected;
        } else {
            objPaintCurrent = objPaintRegular;
        }
        return last;
    }


    //abstract methods

    public abstract int getColor();

    public abstract float getXPos();

    public abstract float getYPos();

    public abstract float getLength();

    public abstract float getWidth();

    public abstract float getRadius();

    public abstract String getFilePath();

    //draws to canvas
    public abstract boolean drawThis();

    public abstract boolean contains(Point test);

    public abstract void translate(float xdis, float ydis);

    public abstract void rotate(float angle);

    public abstract void scale(float factor);

    public abstract void setColor(int color, String action);
}
