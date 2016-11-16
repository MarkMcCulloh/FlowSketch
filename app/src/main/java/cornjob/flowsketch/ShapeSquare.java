package cornjob.flowsketch;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by john on 11/15/2016.
 */
public class ShapeSquare {
    private float height = 400f;
    private float x;
    private float y;
    private Paint square_Paint;

    public ShapeSquare(float x, float y)
    {
        this.x = x;
        this.y = y;
        square_Paint = new Paint();
        square_Paint .setColor(Color.BLACK);
        square_Paint.setStyle(Paint.Style.STROKE);
        square_Paint.setStrokeWidth(10f);

    }
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
    public int getRight() {return (int)(x+height);}
    public int getBottom() {return (int)(y+height);}
    public void setHeighandWidth(int height) {this.height = height;}
    public void setColor(int color){this.square_Paint.setColor(color);}

}
