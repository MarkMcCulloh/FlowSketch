package cornjob.flowsketch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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

    }

    @Override
    public boolean drawThis() {

objCanvas.canvas.drawBitmap(b,x,y,null);

        return true;
    }


    @Override
    public boolean contains(Point test) {
        return false;
    }

    @Override
    public void translate(float xdis, float ydis) {

    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void scale(float factor) {

    }

    @Override
    public void setColor(int color) {

    }
}
