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
    private  int i = 0;

    Point(float x,float y)
    {
        this.x = x;
        this.y =y;

    }
    public float getX()
    {
        return this.x;
    }
    public void setindex(int i)
    {
        this.i = i;
    }
    public int get_Index()
    {
        return this.i;
    }
    public void setX(float x)
    {
        this.x = x;
    }
    public float getY()
    {
        return this.y;
    }
    public void setY(float y)
    {
        this.y = y;
    }
}

