package cornjob.flowsketch;

import android.graphics.BlurMaskFilter;
import android.graphics.Rect;
import android.graphics.RectF;

class ObjectText extends Object {
    public String text = new String();

    ObjectText(MyCanvas mainCanvas, float x, float y, String text, int textsize) {
        super(mainCanvas, x, y, OBJTYPE.TEXT);

        this.text = text;
        objPaintCurrent_Fill.setTextSize(textsize);
        objPaintCurrent_Stroke.setTextSize(textsize);
    }

    ObjectText(MyCanvas mainCanvas, String inString) {
        super(mainCanvas, inString);
        String[] stuff = DECODE(inString);

        this.text = stuff[5];
        objPaintCurrent_Fill.setTextSize(Float.parseFloat(stuff[13]));
        objPaintCurrent_Stroke.setTextSize(Float.parseFloat(stuff[13]));
    }

    public boolean drawThis() {
        if (objSelect) {
            updateSelectBorder();
            selectBorder.setStrokeWidth(objPaintCurrent_Fill.getStrokeWidth() / 6);
            selectBorder.setMaskFilter(new BlurMaskFilter(objPaintCurrent_Stroke.getTextSize() / 10, BlurMaskFilter.Blur.NORMAL));
            objCanvas.canvas.drawText(text, objOrigin.getX(), objOrigin.getY(), selectBorder);
        }
        objCanvas.canvas.drawText(text, objOrigin.getX(), objOrigin.getY(), objPaintCurrent_Fill);
        return true;
    }

    public boolean contains(Point test) {
        Rect bounds = new Rect();

        objPaintCurrent_Fill.getTextBounds(text, 0, text.length(), bounds);

        float mTextWidth = objPaintCurrent_Fill.measureText(text);
        float mTextHeight = bounds.height();

        RectF boundsf = new RectF(objOrigin.getX(), objOrigin.getY(), objOrigin.getX() + mTextWidth, objOrigin.getY() + mTextHeight);

        return (boundsf.contains(test.getX(), test.getY()));
    }

    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    @Override
    public String encode() {
        return ENCODE(objType, objOrigin.getX(), objOrigin.getY(), objPaintCurrent_Fill.getColor(), objPaintCurrent_Stroke.getColor(), -1, -1, -1, -1, -1, -1, text, "-1", objPaintCurrent_Fill.getTextSize(), "-1");
    }


    @Override
    public void scale(float factor) {
        objPaintCurrent_Fill.setTextSize(objPaintCurrent_Fill.getTextSize() * factor);
        objPaintCurrent_Stroke.setTextSize(objPaintCurrent_Stroke.getTextSize() * factor);
    }
}
