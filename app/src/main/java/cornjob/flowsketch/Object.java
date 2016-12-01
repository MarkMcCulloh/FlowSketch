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

    Object(MyCanvas mainCanvas, String inString) {
        String[] stuff = DECODE(inString);
        objCanvas = mainCanvas;
        objOrigin = new Point(Float.parseFloat(stuff[1]), Float.parseFloat(stuff[2]));
        objType = OBJTYPE.valueOf(stuff[0]);

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
    Object Type *ALL        0
    x pos       *ALL        1
    y pos       *ALL        2
    fill        *ALL        3
    stroke      *ALL        4
    height      *RECT       5
    width       *RECT&TRI   6
    p2 x pos    *LINE       7
    p2 y pos    *LINE       8
    height      *TRI        9
    radius      *CIRCLE     10
    text        *TEXT       11
    fonttype    *TEXT       12
    fontsize    *TEXT       13
    imgpath     *PIC        14

     */
    public abstract String encode();

    static String ENCODE(OBJTYPE otype, float xpos, float ypos, int fillc, int strokec, float length, float width, float p2x, float p2y, float height, float radius, String text, String fonttype, float fontsize, String imgpath) {
        String ret = "";

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
        ret += text + ",";
        ret += fonttype + ",";
        ret += fontsize + ",";
        ret += imgpath + ",";

        return ret;
    }

    static String[] DECODE(String inString) {
        String[] stuff = inString.split(",");

        return stuff;
    }

}
