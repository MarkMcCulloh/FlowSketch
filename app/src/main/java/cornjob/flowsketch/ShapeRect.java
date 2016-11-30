package cornjob.flowsketch;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

class ShapeRect extends Object {

    private RectF fullRect;

    ShapeRect(MyCanvas mainCanvas, float x, float y, float l, float w) {
        super(mainCanvas, x, y, OBJTYPE.RECTANGLE);

        fullRect = new RectF(x, y, l, w);
    }

    ShapeRect(MyCanvas mainCanvas, float x, float y, float s) {
        super(mainCanvas, x, y, OBJTYPE.SQUARE);

        fullRect = new RectF(x, y, x + s, y + s);
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
        return ENCODE(objType, objOrigin.getX(), objOrigin.getY(), objPaintCurrent_Fill.getColor(), objPaintCurrent_Stroke.getColor(), fullRect.height(), fullRect.width(), -1, -1, -1, -1, "", "", -1, "");
    }

    @Override
    public Object decode(String inString) {
        return null;
    }


    @Override
    public void scale(float factor) {
        fullRect.set((fullRect.left - 1) * factor, (fullRect.top - 1) * factor, (fullRect.right + 1) * factor, (fullRect.bottom + 1) * factor);

    }

}

