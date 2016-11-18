package cornjob.flowsketch;

import android.graphics.*;

/**
 * Created by MarkM on 11/17/2016.
 */

public abstract class Object {
    public enum OBJTYPE {
        CIRCLE, LINE, TRIANGLE, RECTANGLE, SQUARE,
        TEXT, IMAGE
    }

    public MyCanvas objCanvas;
    public Point objOrigin;
    public Paint objPaintCurrent, objPaintSelected, objPaintRegular;
    public float objScale, objRotate, objTranslate;

    public OBJTYPE objType;
    public boolean objSelect;


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

    //draws to canvas
    public abstract boolean drawThis();

    public abstract boolean contains(Point test);

    public abstract void translate(float xdis, float ydis);

    public abstract void rotate(float angle);

    public abstract void scale(float factor);
}
