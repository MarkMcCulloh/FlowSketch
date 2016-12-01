package cornjob.flowsketch;

import android.graphics.Bitmap;
import android.graphics.RectF;

class ObjectPic extends Object {
    private Bitmap b;


    ObjectPic(MyCanvas mainCanvas, float x, float y, Bitmap b) {
        super(mainCanvas, x, y, OBJTYPE.IMAGE);
        this.b = b;
    }

    @Override
    public boolean drawThis() {
        if (objSelect) {
            updateSelectBorder();
            RectF drawrect = new RectF(objOrigin.getX(), objOrigin.getY(), b.getWidth(), b.getHeight());
            objCanvas.canvas.drawRect(drawrect, selectBorder);
        }
        objCanvas.canvas.drawBitmap(b, objOrigin.getX(), objOrigin.getY(), null);
        return true;
    }


    @Override
    public boolean contains(Point test) {
        float x = objOrigin.getX();
        float y = objOrigin.getY();
        return test.getX() >= x && test.getX() < (x + b.getWidth()) && test.getY() >= y && test.getY() < (y + b.getHeight());
    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    @Override
    public String encode() {
        return ENCODE(objType, objOrigin.getX(), objOrigin.getY(), -1, -1, -1, b.getWidth(), -1, -1,b.getHeight(), -1, "", "", -1, "");
        // String text, String fonttype, float fontsize,
    }

    @Override
    public Object decode(String inString) {
        return null;
    }

    @Override
    public void scale(float factor) {
        float width = b.getWidth() * factor;
        float height = b.getHeight() * factor;
       /*float scalewidth = width/b.getWidth();
        float scaleheight = height/b.getHeight();
        Matrix m = new Matrix();
        m.postScale(scalewidth,scaleheight);
        b = Bitmap.createBitmap(b,(int)objOrigin.getX(),(int)objOrigin.getY(),(int)width,(int)height,m,true);*/
        b = Bitmap.createScaledBitmap(b, (int) width, (int) height, true);
    }
}
