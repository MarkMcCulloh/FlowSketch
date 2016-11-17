package cornjob.flowsketch;

/**
 * Created by john on 11/14/2016.
 */

/**
 * Created by john on 11/5/2016.
 */
public class Point{
    private float x ;
    private float y ;
    private int i = 0;

    Point(float x,float y)
    {
        this.x = x;
        this.y = y;
    }

    float getX()
    {
        return this.x;
    }

    void setX(float x)
    {
        this.x = x;
    }

    float getY()
    {
        return this.y;
    }

    void setY(float y)
    {
        this.y = y;
    }

    void move(float xdis, float ydis) {
        this.x += xdis;
        this.y += ydis;
    }

    static float distance(Point p1, Point p2) {
        return (float) Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getX());
    }
}

