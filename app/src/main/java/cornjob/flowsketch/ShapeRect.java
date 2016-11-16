package cornjob.flowsketch;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by john on 11/14/2016.
 */
public class ShapeRect {
    private float x = 300;
    private float y = 400;
    private int Height;
    private int Width;
    private boolean marked;
    private Paint rectPaint;
    public ShapeRect(float x, float y)
    {
        this.x = x;
        this.y =y;
        rectPaint = new Paint();
        rectPaint .setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(10f);
    }
    public Paint getPaint(){return this.rectPaint;}
    public void setPaintcolor(int color){this.rectPaint.setColor(color);}
    public void setTopx(float x)
    {
        this.x = x;
    }
    public int getTopx()
    {
        return (int)x;
    }
    public void setTopy(float y) {this.y =y;}
    public int getTopy()
    {
        return (int)y;
    }
    public int getRight() {return (int)x+Width;}
    public int getBottom() {return (int)y+Width;}
    public void setHeight(int height) {this.Height = height;}
    public void setWidth(int Width)
    {
        this.Width = Width;
    }
    public void setMarked(boolean marked){this.marked = marked;}
    public boolean getMarked(){return marked;}

}
