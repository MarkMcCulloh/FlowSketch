package cornjob.flowsketch;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by john on 11/15/2016.
 */
public class objectText extends Object {
    public String text = new String();

    public objectText(MyCanvas mainCanvas, float x, float y, String text)
    {
        super(mainCanvas, x, y, OBJTYPE.TEXT);

        objPaintRegular = new Paint();
        objPaintRegular.setColor(Color.BLACK);
        objPaintRegular.setStyle(Paint.Style.FILL);
        objPaintRegular.setTypeface(canvasFragment.face);
        objPaintRegular.setTextSize(40);

        objPaintSelected = new Paint();
        objPaintSelected.setColor(Color.YELLOW);
        objPaintSelected.setStyle(Paint.Style.FILL);
        objPaintSelected.setTypeface(canvasFragment.face);
        objPaintSelected.setTextSize(40);

        objPaintCurrent = objPaintRegular;

        this.text = text;
    }

    @Override
    public float getRadius() {
        return 0;
    }

    @Override
    public String getFilePath() {
        return "";
    }

    public  boolean drawThis(){
        objCanvas.canvas.drawText(text, objOrigin.getX(), objOrigin.getY(), objPaintCurrent);
        return true;
    }

    public  boolean contains(Point test){

        Rect bounds = new Rect();

        objPaintCurrent.getTextBounds(text, 0, text.length(), bounds);

        RectF boundsf = new RectF(bounds);

        boundsf.offsetTo(objOrigin.getX(), objOrigin.getY());

        return (boundsf.contains(test.getX(), test.getY()));
    }

    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    public void rotate(float angle){}

    public void scale(float factor) {
        objPaintRegular.setTextSize(objPaintRegular.getTextSize() * factor);
        objPaintSelected.setTextSize(objPaintRegular.getTextSize());
        objPaintCurrent.setTextSize(objPaintRegular.getTextSize());
    }

    public void setColor(int color,String action){}

    @Override
    public int getColor() {
        return objPaintCurrent.getColor();
    }

    @Override
    public float getXPos() {
        return objOrigin.getX();
    }

    @Override
    public float getYPos() {
        return objOrigin.getY();
    }

    @Override
    public float getLength() {
        return -1f;
    }

    @Override
    public float getWidth() {
        return -1f;
    }
}
