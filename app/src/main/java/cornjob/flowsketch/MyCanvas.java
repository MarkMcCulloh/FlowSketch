package cornjob.flowsketch;

//current issues:
//crash when selected to add shape menu after adding object and nothing else
//object positions are not properly scaled in the view

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by john on 9/25/2016.
 */
public class MyCanvas extends View {
    Context context;
    private static ArrayList<Object> Objects = new ArrayList<>();
    public static Bundle mMyAppsBundle = new Bundle();

    public static boolean is_Marked, remove_Object, inputText, verify_Text = false;
    private Object selectedobj;
    private ScaleGestureDetector mScaleDetector;
    public static float mScaleFactor = 5.f;
    private int mActivePointerId;

    private EditText editxt;

    private float mLastTouchX, mLastTouchY;
    private float scalePointX, scalePointY;
    private float mPosX, mPosY;
    private float cX, cY;

    private Paint basicPaint;

    private Object.OBJTYPE nextShape;
    private boolean newObject = false;

    public String text;

    public Canvas canvas;
    private Bitmap bitmap;
    private static final int MAX_CLICK_DURATION = 150;
    private long startClickTime;

    public MyCanvas(Context c, AttributeSet attributeSet )
    {
        super(c,attributeSet);
        context = c;

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        selectedobj = null;

        canvas = new Canvas();

        MyCanvas.mMyAppsBundle.putString("is_marked","value");

        basicPaint = new Paint();
        basicPaint.setColor(Color.BLACK);
        basicPaint.setStyle(Paint.Style.STROKE);
        basicPaint.setStrokeWidth(10f);

    }

    public static void objectToString(){

        for(Object obj: Objects)
        {
            if(obj instanceof ShapeRect){
                CanvasData.data += "[" + obj.objType + ",";
                CanvasData.data += obj.getXPos() + ",";
                CanvasData.data += obj.getYPos() + ",";
                CanvasData.data += obj.getLength() + ",";
                CanvasData.data += obj.getWidth() + ",";
                CanvasData.data += obj.getRadius() + ",";
                CanvasData.data += obj.getColor() +"]";
            }
        }

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

    }


    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        this.canvas = canvas;

        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);

        canvas.drawLine(0, -1000, 0, 1000, basicPaint);
        canvas.drawLine(-1000, 0, 1000, 0, basicPaint);

        canvas.drawCircle(cX, cY, 10, basicPaint);

        for (Object obj : Objects) {
            obj.drawThis();
        }


        canvas.restore();
    }

    /** Delete objects on canvas
     * - This is called from "canvasFragment.onOptionsItemSelected()" **/
    public static void DeleteObject()
    {
        remove_Object = true;
    }


    //add different Objects, text and remove
    public void setText()
    {
        inputText = true;
    }
    public void reset()
    {
        Objects.clear();
        newObject = false;
        invalidate();
    }

    public void addObject(Object.OBJTYPE newobj) {
        newObject = true;
        nextShape = newobj;
        switch (newobj) {
            case CIRCLE:
                Objects.add(new ShapeCircle(this, this.cX, this.cY, 100));
                break;
            case LINE:
                //Objects.add(new ShapeLine(this.getWidth() / 2, this.getHeight() / 2, 240));
                break;
            case TRIANGLE:
                Objects.add(new ShapeTriangle(this, this.cX, this.cY, this.cX + 100, this.cY + 100));
                break;
            case RECTANGLE:
                Objects.add(new ShapeRect(this, this.cX, this.cY, this.cX + 100, this.cY + 100));
                break;
            case SQUARE:
                Objects.add(new ShapeRect(this, this.cX, this.cY, 100));
                break;
            case TEXT:
                //Objects.add(new ObjectText(this.getWidth() / 2, this.getHeight() / 2, 240));
                break;
            case IMAGE:
                //Objects.add(new ObjectPic();
                break;
        }
        invalidate();
        newObject = false;
    }

    public void setAddText()
    {
        verify_Text = true;
    }

    public void delete()
    {
        remove_Object = true;
        DeleteObject();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.setQuickScaleEnabled(true);
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        final int action = ev.getAction();


        float[] mClickCoords = new float[2];

        //translate screen event into canvas coords
        mClickCoords[0] = ev.getX();
        mClickCoords[1] = ev.getY();

        Matrix matrix = new Matrix();
        matrix.set(getMatrix());

        //this is where you apply any translations/scaling/rotation etc.
        //Typically you want to apply the same adjustments that you apply
        //in your onDraw().

        matrix.preTranslate(mPosX, mPosY);
        matrix.preScale(mScaleFactor, mScaleFactor);

        // invert the matrix, creating the mapping from screen
        //coordinates to canvas coordinates
        matrix.invert(matrix);

        //apply the mapping
        matrix.mapPoints(mClickCoords);

        //mClickCoords[0] is the canvas x coordinate and
        //mClickCoords[1] is the y coordinate.

        //final float x = (ev.getX() - scalePointX) / mScaleFactor;
        //final float y = (ev.getY() - scalePointY) / mScaleFactor;
        final float x = (ev.getX()) / mScaleFactor;
        final float y = (ev.getY()) / mScaleFactor;
        cX = mClickCoords[0];
        cY = mClickCoords[1];

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                startClickTime = Calendar.getInstance().getTimeInMillis();
                mLastTouchX = x;
                mLastTouchY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress()) {
                    final float dx = x - mLastTouchX; // change in X
                    final float dy = y - mLastTouchY; // change in Y

                    if (selectedobj != null) {
                        selectedobj.translate(dx, dy);
                    } else {
                        mPosX += dx;
                        mPosY += dy;
                    }
                    invalidate();
                }

                mLastTouchX = x;
                mLastTouchY = y;
                break;

            }
            case MotionEvent.ACTION_UP: {

                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION) {
                    //selects object if tapped
                    //deselected all objects if nothing was tapped

                    boolean flag = false;
                    for (Object obj : Objects) {
                        if (obj.contains(new Point(cX, cY))) {
                            if (selectedobj != null) selectedobj.setSelect(false);
                            obj.setSelect(true);
                            selectedobj = obj;
                            flag = true;
                        }
                    }
                    if (!flag) {
                        if (selectedobj != null) selectedobj.setSelect(false);
                        selectedobj = null;
                    }

                }

                invalidate();
            }
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (selectedobj != null) {
                //scale selected
                selectedobj.scale(detector.getScaleFactor());
            } else {
                //scale canvas
                mScaleFactor *= detector.getScaleFactor();
                scalePointX = detector.getFocusX();
                scalePointY = detector.getFocusY();
            }

            invalidate();
            return true;
        }
    }


}

