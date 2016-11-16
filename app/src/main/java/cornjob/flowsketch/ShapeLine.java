package cornjob.flowsketch;

/**
 * Created by john on 11/14/2016.
 */

/**
 * Created by john on 11/5/2016.
 */
public class ShapeLine {

    int i;
    Point point1;
    Point point2;

    public ShapeLine(Point point1, Point point2)
    {
        this.point1 = point1;
        this.point2 = point2;


    }
    public void setPoint(float x, float y, int i)
    {
        if(i == 0)
        {
            point1.setX(x);
            point1.setY(y);
        }
        else if(i ==1)
        {
            point2.setX(x);
            point2.setY(y);
        }

    }
    public Point getPoint(int pt)
    {
        if(pt == 0)
        {
            return point1;

        }
        else
        {
            return point2;
        }

    }
    public Point getPoint1()
    {
        return point1;
    }
    public Point getPoint2()
    {
        return point2;
    }



}

