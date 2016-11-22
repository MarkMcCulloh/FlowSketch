package cornjob.flowsketch;

import android.graphics.Paint;
import android.graphics.Color;

public class ShapeCircle extends Object {

    private float circleRadius;

    ShapeCircle(MyCanvas mainCanvas, float x, float y, float r) {
        super(mainCanvas, x, y, OBJTYPE.CIRCLE);
        objOrigin = new Point(x, y);
        circleRadius = r;

        objPaintRegular = new Paint();
        objPaintRegular.setColor(Color.BLACK);
        objPaintSelected.setStyle(Paint.Style.STROKE);
        objPaintRegular.setStrokeWidth(10f);

        objPaintSelected = new Paint();
        objPaintSelected.setColor(Color.YELLOW);
        objPaintSelected.setStyle(Paint.Style.STROKE);
        objPaintSelected.setStrokeWidth(10f);

        objPaintCurrent = objPaintRegular;
    }

    @Override
    public int getColor() {
        return objPaintRegular.getColor();
    }

    @Override
    public float getXPos() {
        return -1;
    }

    @Override
    public float getYPos() {
        return -1;
    }

    @Override
    public float getLength() {
        return -1;
    }

    @Override
    public float getWidth() {
        return -1;
    }

    @Override
    public float getRadius() {
        return circleRadius;
    }

    @Override
    public String getFilePath() {
        return null;
    }

    @Override
    public boolean drawThis() {
        objCanvas.canvas.drawCircle(objOrigin.getX(), objOrigin.getY(), circleRadius, objPaintCurrent);
        return true;
    }

    @Override
    public boolean contains(Point test) {
        return Point.distance(objOrigin, test) <= circleRadius;
    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    @Override
    public void rotate(float angle) {
        //circle lol
    }

    @Override
    public void scale(float factor) {
        circleRadius *= factor;
    }


    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }
    public void setColor(int color,String action){
        objPaintRegular.setColor(color);
        if(action == "Fill") {
            objPaintRegular.setStyle(Paint.Style.FILL);

        }
        else
        {
         objPaintRegular.setStyle(Paint.Style.STROKE);
        }
    }
}

