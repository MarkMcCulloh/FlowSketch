package cornjob.flowsketch;

import android.graphics.RectF;
import android.util.Log;

class ShapeRect extends Object {

    private RectF fullRect;

    ShapeRect(MyCanvas mainCanvas, float x, float y, float l, float w) {
        super(mainCanvas, x, y, OBJTYPE.RECTANGLE);

        fullRect = new RectF(x, y, x + l, y + w);
    }

    ShapeRect(MyCanvas mainCanvas, float x, float y, float s) {
        super(mainCanvas, x, y, OBJTYPE.SQUARE);

        fullRect = new RectF(x, y, x + s, y + s);
    }

    ShapeRect(MyCanvas mainCanvas, String inString) {
        super(mainCanvas, inString);
        String[] stuff = DECODE(inString);

        float x = Float.parseFloat(stuff[1]);
        float y = Float.parseFloat(stuff[2]);
        float l = Float.parseFloat(stuff[5]);
        float w = Float.parseFloat(stuff[6]);
        Log.i("RECT", inString);
        fullRect = new RectF(x, y, x + w, y + l);
    }

    @Override
    public boolean drawThis() {
        if (objSelect) {
            updateSelectBorder();
            objCanvas.canvas.drawRect(fullRect, selectBorder);
            objCanvas.canvas.drawRect(fullRect, selectBorder);
        }
        objCanvas.canvas.drawRect(fullRect, objPaintCurrent_Fill);
        objCanvas.canvas.drawRect(fullRect, objPaintCurrent_Stroke);

        return true;
    }

    @Override
    public boolean contains(Point test) {
        return fullRect.contains(test.getX(), test.getY());

    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
        fullRect.set(objOrigin.getX(), objOrigin.getY(), fullRect.width() + objOrigin.getX(), fullRect.height() + objOrigin.getY());
    }

    @Override
    public String encode() {
        return ENCODE(objType, objOrigin.getX(), objOrigin.getY(), objPaintCurrent_Fill.getColor(), objPaintCurrent_Stroke.getColor(), fullRect.height(), fullRect.width(), -1, -1, -1, -1, "-1", "-1", -1, "-1");
    }

    @Override
    public void scale(float factor) {
        fullRect.set(fullRect.left, fullRect.top, fullRect.left + (fullRect.width() * factor), fullRect.top + (fullRect.height() * factor));

    }

}

