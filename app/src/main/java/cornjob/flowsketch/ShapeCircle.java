package cornjob.flowsketch;

import android.graphics.Paint;
import android.graphics.Color;
/**
 * Created by john on 11/14/2016.
 */

public class ShapeCircle {

    private Paint Circle_Paint;
    private float radius = 230.0f;
    private float x;
    private float y;
    ShapeCircle(float x, float y){

        this.x = x;
        this.y =y;

        Circle_Paint = new Paint();
        Circle_Paint.setColor(Color.BLACK);
        Circle_Paint.setStyle(Paint.Style.STROKE);
        Circle_Paint.setStrokeWidth(10f);
    }
    public float getRadius()
    {
        return radius;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){this.y = y;}
    public Paint getPaint(){return Circle_Paint ;}
    public float getArea(){
        return  (float)Math.PI * (radius * radius);
    }
    public void setColor(int color){this.Circle_Paint.setColor(color);}
    public void setRadius(float rad)
    {
        this.radius = rad;
    }
}

