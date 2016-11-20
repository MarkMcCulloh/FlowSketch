package cornjob.flowsketch;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by john on 11/16/2016.
 */

public class ShapeTriangle extends Object {
    private Point p1, p2, p3;
    private Path triPath;
    private float triWidth, triHeight;

    ShapeTriangle(MyCanvas mainCanvas, float x, float y, float w, float h) {
        super(mainCanvas, x, y, OBJTYPE.TRIANGLE);

        triWidth = w;
        triHeight = h;

        objPaintRegular = new Paint();
        objPaintRegular.setColor(Color.BLACK);
        objPaintRegular.setStyle(Paint.Style.STROKE);
        objPaintRegular.setStrokeWidth(10f);

        objPaintSelected = new Paint();
        objPaintSelected.setColor(Color.YELLOW);
        objPaintSelected.setStyle(Paint.Style.STROKE);
        objPaintSelected.setStrokeWidth(10f);

        objPaintCurrent = objPaintRegular;
    }

    @Override
    public boolean drawThis() {

        p1 = objOrigin;
        float pointX = objOrigin.getX() + triWidth / 2;
        float pointY = objOrigin.getY() + triHeight;

        p2 = new Point(pointX, pointY);
        p3 = new Point(objOrigin.getX() + triWidth, objOrigin.getY());


        Path path = new Path();
        path.moveTo(p1.getX(), p1.getY());
        path.lineTo(p2.getX(), p2.getY());
        path.lineTo(p3.getX(), p3.getY());
        path.close();

        objCanvas.canvas.drawPath(path, objPaintCurrent);
        return true;
    }

    @Override
    public boolean contains(Point test) {
        //dat math
        double Area = 0.5 * (-p2.getY() * p3.getX() + p1.getY() * (-p2.getX() + p3.getX()) + p1.getX() * (p2.getY() - p3.getY()) + p2.getX() * p3.getY());
        double s = 1 / (2 * Area) * (p1.getY() * p3.getX() - p1.getX() * p3.getY() + (p3.getY() - p1.getY()) * test.getX() + (p1.getX() - p3.getX()) * test.getY());
        double t = 1 / (2 * Area) * (p1.getX() * p2.getY() - p1.getY() * p2.getX() + (p1.getY() - p2.getY()) * test.getX() + (p2.getX() - p1.getX()) * test.getY());
        return s > 0 && t > 0 && 1 - s - t > 0;
    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void scale(float factor) {
        triHeight *= factor;
        triWidth *= factor;
    }
}
