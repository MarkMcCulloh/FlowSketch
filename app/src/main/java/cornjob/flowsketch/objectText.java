package cornjob.flowsketch;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by john on 11/15/2016.
 */
public class objectText extends Object {
    private String text = new String();
    private float startx;
    private float starty;

     public objectText(MyCanvas mainCanvas,float x,float y,String text)
    {
        super(mainCanvas,x,y, OBJTYPE.CIRCLE);
        startx = x;
        starty = y;
        objPaintRegular = new Paint();
        objPaintRegular.setColor(Color.BLACK);
        objPaintRegular.setStyle(Paint.Style.FILL);
        objPaintRegular.setTextSize(20);
        this.text =text;
            }

    @Override
    public float getRadius() {
        return 0;
    }

    public  boolean drawThis(){
        objCanvas.canvas.drawText(text,startx,starty,objPaintRegular);
        return true;
    }

    public  boolean contains(Point test){
        return false;
    }

    public void translate(float xdis, float ydis){}

    public void rotate(float angle){}

    public void scale(float factor){}

    public void setColor(int color){}

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
        return -1f;
    }

    @Override
    public float getWidth() {
        return -1f;
    }
}
