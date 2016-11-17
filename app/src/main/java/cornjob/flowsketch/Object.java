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

    public Point objOrigin;
    public Paint objPaint;
    public float objScale, objRotate, objTranslate;

    public OBJTYPE objType;
    public boolean objSelect;

    //abstract methods
    public abstract boolean drawThis(Canvas objCanvas);

    public abstract boolean contains(Point test);

    public abstract void translate(float xdis, float ydis);

    public abstract void rotate(float angle);

    public abstract void scale(float factor);
}
