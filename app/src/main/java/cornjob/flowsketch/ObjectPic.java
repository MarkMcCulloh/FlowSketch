package cornjob.flowsketch;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by MarkM on 11/17/2016.
 */


public class ObjectPic extends Object {
    private Bitmap b;
    private float x;
    private float y;
    ObjectPic(MyCanvas mainCanvas, float x, float y, Bitmap b) {
        super(mainCanvas, x, y, OBJTYPE.IMAGE);
        this.b = b;
        objOrigin = new Point(x,y);

    }

    @Override
    public boolean drawThis() {

    objCanvas.canvas.drawBitmap(b,objOrigin.getX(),objOrigin.getY(),null);

        return true;
    }


    @Override
    public boolean contains(Point test) {
        if(test.getX()>= x && test.getX()<(x+b.getWidth()) && test.getY() >= y && test.getY() < (y + b.getHeight()))
        {
            return true;

        }
        else
        {
            return false;
        }
    }

    @Override
    public void translate(float xdis, float ydis) {

       objOrigin.move(xdis,ydis);

    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void scale(float factor) {
        float width = b.getWidth() *factor;
        float height = b.getHeight() * factor;
       /*float scalewidth = width/b.getWidth();
        float scaleheight = height/b.getHeight();
        Matrix m = new Matrix();
        m.postScale(scalewidth,scaleheight);
        b = Bitmap.createBitmap(b,(int)objOrigin.getX(),(int)objOrigin.getY(),(int)width,(int)height,m,true);*/
        b = Bitmap.createScaledBitmap(b,(int)width,(int)height,true);
    }

    @Override
    public void setColor(int color,String action) {

    }

    @Override
    public float getRadius() {
        return 0;
    }

    @Override
    public String getFilePath() {
        return "hi";
    }
    @Override
    public int getColor() {
        return -1;
    }

    @Override
    public float getXPos() {
        return x;
    }

    @Override
    public float getYPos() {
        return y;
    }

    @Override
    public float getLength() {
        return -1;
    }

    @Override
    public float getWidth() {
        return -1;
    }
}
