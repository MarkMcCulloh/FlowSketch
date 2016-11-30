package cornjob.flowsketch;

import android.graphics.Path;

class ShapeTriangle extends Object {
    private Point p1, p2, p3;

    private float triWidth, triHeight;


    ShapeTriangle(MyCanvas mainCanvas, float x, float y, float w, float h) {
        super(mainCanvas, x, y, OBJTYPE.TRIANGLE);

        triWidth = w;
        triHeight = h;
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

        if (objSelect) {
            updateSelectBorder();
            objCanvas.canvas.drawPath(path, selectBorder);
            objCanvas.canvas.drawPath(path, selectBorder);
        }

        objCanvas.canvas.drawPath(path, objPaintCurrent_Fill);
        objCanvas.canvas.drawPath(path, objPaintCurrent_Stroke);
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
    public void scale(float factor) {
        triHeight *= factor;
        triWidth *= factor;
    }


    @Override
    public String encode() {
        return ENCODE(objType, objOrigin.getX(), objOrigin.getY(), objPaintCurrent_Fill.getColor(), objPaintCurrent_Stroke.getColor(), -1, triWidth, -1, -1, triHeight, -1, "", "", -1, "");
    }

    @Override
    public Object decode(String inString) {
        return null;
    }
}
