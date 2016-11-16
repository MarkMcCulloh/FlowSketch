package cornjob.flowsketch;

import android.graphics.Path;

/**
 * Created by john on 11/16/2016.
 */
public class ShapeTriangle {
    private Point one;
    private Point two;
    private Point three;
    private Path tri_Path;
    private float height = 400f;
    private float width = 300f;
    public ShapeTriangle(Point one)
    {
        this.one = new Point(one.getX(),one.getY());
        this.two = new Point(one.getX()-width,one.getY()+height);
        this.three = new Point(one.getX()+width,one.getY()+height);
        tri_Path = new Path();
        tri_Path.moveTo(this.one.getX(),this.one.getY());
        tri_Path.lineTo(this.one.getX(),this.one.getY());
        tri_Path.lineTo(two.getX(),two.getY());
        tri_Path.lineTo(three.getX(),three.getY());
        tri_Path.close();
    }
    public Path getTri_Path()
    {
        return this.tri_Path;
    }
}
