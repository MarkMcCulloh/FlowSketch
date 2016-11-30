package cornjob.flowsketch;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class Object {

    enum OBJTYPE {
        CIRCLE, LINE, TRIANGLE, RECTANGLE, SQUARE,
        TEXT, IMAGE, LINER
    }

    MyCanvas objCanvas;
    Point objOrigin;
    Paint objPaintCurrent_Stroke;
    Paint objPaintCurrent_Fill;
    Paint selectBorder;

    OBJTYPE objType;
    boolean objSelect;

    Object(MyCanvas mainCanvas, float x, float y, OBJTYPE type) {
        objCanvas = mainCanvas;
        objOrigin = new Point(x, y);
        objType = type;

        selectBorder = new Paint();

        objPaintCurrent_Stroke = new Paint();
        objPaintCurrent_Stroke.setColor(Color.BLACK);
        objPaintCurrent_Stroke.setStyle(Paint.Style.STROKE);
        objPaintCurrent_Stroke.setStrokeWidth(10f);
        objPaintCurrent_Stroke.setAntiAlias(true);

        objPaintCurrent_Fill = new Paint();
        objPaintCurrent_Fill.setColor(Color.BLACK);
        objPaintCurrent_Fill.setStyle(Paint.Style.FILL);
        objPaintCurrent_Fill.setStrokeWidth(10f);
        objPaintCurrent_Fill.setAntiAlias(true);

    }


    public boolean setSelect(boolean flag) {
        boolean last = objSelect;
        objSelect = flag;
        return last;
    }


    public void setStroke(int color) {
        objPaintCurrent_Stroke.setColor(color);
        objCanvas.invalidate();
    }

    public void setFill(int color) {
        objPaintCurrent_Fill.setColor(color);
        objCanvas.invalidate();
    }

    void updateSelectBorder() {
        selectBorder.set(objPaintCurrent_Stroke);
        selectBorder.setDither(true);
        selectBorder.setColor(Color.BLUE);
        selectBorder.setStrokeWidth(objPaintCurrent_Stroke.getStrokeWidth() * 2.0f);
        selectBorder.setMaskFilter(new BlurMaskFilter(objPaintCurrent_Stroke.getStrokeWidth() * 60, BlurMaskFilter.Blur.NORMAL));
    }

    //abstract methods

    //draws to canvas
    public abstract boolean drawThis();

    public abstract boolean contains(Point test);

    public abstract void scale(float factor);

    public abstract void translate(float xdis, float ydis);

    /*
    OBJECT DATA STRING
    ________________
    Object Type *ALL
    x pos       *ALL
    y pos       *ALL
    fill        *ALL
    stroke      *ALL
    height      *RECT
    width       *RECT&TRI
    p2 x pos    *LINE
    p2 y pos    *LINE
    height      *TRI
    radius      *CIRCLE
    text        *TEXT
    fonttype    *TEXT
    fontsize    *TEXT
    imgpath     *PIC

     */
    public abstract String encode();

    public abstract Object decode(String inString);

    static String ENCODE(OBJTYPE otype, float xpos, float ypos, int fillc, int strokec, float length, float width, float p2x, float p2y, float height, float radius, String text, String fonttype, float fontsize, String imgpath) {
        String ret = "[";

        ret += otype + ",";
        ret += xpos + ",";
        ret += ypos + ",";
        ret += fillc + ",";
        ret += strokec + ",";
        ret += length + ",";
        ret += width + ",";
        ret += p2x + ",";
        ret += p2y + ",";
        ret += height + ",";
        ret += radius + ",";
        ret += fonttype + ",";
        ret += fontsize + ",";
        ret += imgpath + ",";

        ret += "]";

        return ret;
    }

}
