package cornjob.flowsketch;

//current issues:
//crash when selected to add shape menu after adding object and nothing else
//object positions are not properly scaled in the view

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;


/**
 * Created by john on 9/25/2016.
 */
public class MyCanvas extends View {
    Context context;

    private int i, lineCount = 0;
    private static Point eventPoint;
    public static boolean is_Marked, already_marked,remove_Object,inputText,object_Move,verify_Text =false;
    private Object selectedobj;
    private ScaleGestureDetector mScaleDetector;
    public static float mScaleFactor = 1.f;
    private float scalePointX;
    private float scalePointY;

    public static Bundle mMyAppsBundle = new Bundle();

    private EditText editxt;
    private static ArrayList<Object> Objects = new ArrayList<>();

    private Paint linePaint;
    private Paint Circle_Paint;
    private Paint rectPaint;
    private Paint point_Paint;
    private Paint Marked;

    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private float cX, cY; // circle coords
    private Rect rect;

    private Object.OBJTYPE nextShape;
    private boolean newObject = false;

    public String text;

    private Path myPath;
    private Canvas canvas;
    private Bitmap bitmap;

    public MyCanvas(Context c, AttributeSet attributeSet )
    {
        super(c,attributeSet);
        context = c;

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        selectedobj = null;

        MyCanvas.mMyAppsBundle.putString("is_marked","value");

        //Circle
        Circle_Paint = new Paint();
        Circle_Paint.setColor(Color.BLACK);
        Circle_Paint.setStyle(Paint.Style.STROKE);
        Circle_Paint.setStrokeWidth(10f);

        //Rect
        rectPaint = new Paint();
        rectPaint .setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(10f);

        linePaint = new Paint();
        linePaint .setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(10f);

        point_Paint =  new Paint();
        point_Paint.setColor(Color.BLACK);
        point_Paint.setStyle(Paint.Style.STROKE);
        point_Paint.setStrokeWidth(30f);

        //Marked
        Marked = new Paint();
        Marked.setColor(Color.GREEN);
        Marked.setStyle(Paint.Style.STROKE);
        Marked.setStrokeWidth(10f);


    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

    }


    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //canvas.getClipBounds(rect);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.translate(mPosX, mPosY);


        for (Object obj : Objects) {
            obj.drawThis(canvas);
        }


        canvas.restore();
    }

    /** Delete objects on canvas
     * - This is called from "canvasFragment.onOptionsItemSelected()" **/
    public static void DeleteObject()
    {
        for (Object obj : Objects) {
            if (obj.contains(eventPoint)) {
                //remove object
            }
        }
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
                Objects.add(new ShapeCircle(this.getWidth() / 2, this.getHeight() / 2, 240));
                break;
            case LINE:
                //Objects.add(new ShapeLine(this.getWidth() / 2, this.getHeight() / 2, 240));
                break;
            case TRIANGLE:
                //Objects.add(new ShapeTriangle(this.getWidth() / 2, this.getHeight() / 2, 240));
                break;
            case RECTANGLE:
                Objects.add(new ShapeRect(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 4, this.getHeight() / 4));
                break;
            case SQUARE:
                Objects.add(new ShapeRect(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 4));
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
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        final int action = ev.getAction();


        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = (ev.getX() - scalePointX) / mScaleFactor;
                final float y = (ev.getY() - scalePointY) / mScaleFactor;
                cX = x - mPosX + scalePointX; // canvas X
                cY = y - mPosY + scalePointY; // canvas Y
                mLastTouchX = x;
                mLastTouchY = y;

                boolean flag = false;
                for (Object obj : Objects) {
                    if (obj.contains(new Point(x, y))) {
                        if (selectedobj != null) selectedobj.objPaint = Circle_Paint;
                        selectedobj = obj;
                        obj.objPaint = Marked;
                        flag = true;
                    }
                }
                if (!flag) {
                    if (selectedobj != null) selectedobj.objPaint = Circle_Paint;
                    selectedobj = null;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                final float x = (ev.getX() - scalePointX) / mScaleFactor;
                final float y = (ev.getY() - scalePointY) / mScaleFactor;
                cX = x - mPosX + scalePointX; // canvas X
                cY = y - mPosY + scalePointY; // canvas Y
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
                final float x = (ev.getX() - scalePointX) / mScaleFactor;
                final float y = (ev.getY() - scalePointY) / mScaleFactor;
                cX = x - mPosX + scalePointX; // canvas X
                cY = y - mPosY + scalePointY; // canvas Y
                mLastTouchX = 0;
                mLastTouchY = 0;
                invalidate();
            }
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (selectedobj != null) {

            } else {
                mScaleFactor *= detector.getScaleFactor();
                scalePointX = detector.getFocusX();
                scalePointY = detector.getFocusY();
            }


            invalidate();
            return true;
        }
    }


}

