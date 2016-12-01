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

import java.util.ArrayList;
import java.util.Calendar;

public class MyCanvas extends View {
    Context context;
    public static ArrayList<Object> Objects = new ArrayList<>();
    public static Bundle mMyAppsBundle = new Bundle();

    public static int current_canvas_id=0;
    public Object selectedobj;
    private ScaleGestureDetector mScaleDetector;
    public static float mScaleFactor = 1.0f;
    private int mActivePointerId;
    int addLine;
    Point lp1, lp2;
    Object.OBJTYPE currentLine;

    private float mLastTouchX, mLastTouchY;
    private float scalePointX, scalePointY;
    private float mPosX, mPosY;
    private float cX, cY;

    private Paint basicPaint;

    private Object.OBJTYPE nextShape;

    public String text = new String();

    public Canvas canvas;
    private Bitmap bitmap, imageMap;
    public static String filePath;
    private static final int MAX_CLICK_DURATION = 150;
    private long startClickTime;

    public MyCanvas(Context c, AttributeSet attributeSet) {
        super(c, attributeSet);
        context = c;

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        selectedobj = null;

        canvas = new Canvas();

        MyCanvas.mMyAppsBundle.putString("is_marked", "value");

        basicPaint = new Paint();
        basicPaint.setColor(Color.BLACK);
        basicPaint.setStyle(Paint.Style.STROKE);
        basicPaint.setStrokeWidth(3f);

        addLine = 0;

        mPosX = 600;
        mPosY = 600;
    }

    public static void objectToString() {
        for (Object obj : Objects) {
            CanvasData.data += obj.encode();
        }
    }

    public ArrayList<Object> StringToObject(){
        ArrayList<Object> obj= new ArrayList<>();
        return obj;
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.canvas = canvas;

        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);

        canvas.drawColor(Color.GRAY);

        canvas.drawLine(0, -10000, 0, 10000, basicPaint);
        canvas.drawLine(-10000, 0, 10000, 0, basicPaint);

        canvas.drawCircle(cX, cY, 40, basicPaint);

        for (Object obj : Objects) {
            obj.drawThis();
        }


        canvas.restore();
    }

    public void reset() {
        Objects.clear();
        CanvasData.data = "";
        invalidate();
    }

    public void setBitmap(Bitmap b) {
        this.imageMap = b;
    }

    public void setColor(int color, String action) {
        if (action.compareTo("Stroke") == 0) {
            selectedobj.setStroke(color);
        } else {
            selectedobj.setFill(color);
        }

    }

    public void delete() {
        Objects.remove(selectedobj);
        selectedobj = null;
        invalidate();
    }

    public void addObject(Object.OBJTYPE newobj) {
        nextShape = newobj;

        float middlex = this.getWidth() / 2;
        float middley = this.getHeight() / 2 - 80;
        float[] mClickCoords = new float[2];

        //translate screen event into canvas coords
        mClickCoords[0] = middlex;
        mClickCoords[1] = middley;
        Matrix matrix = new Matrix();
        matrix.set(getMatrix());
        matrix.preTranslate(mPosX, mPosY);
        matrix.preScale(mScaleFactor, mScaleFactor);
        matrix.invert(matrix);
        matrix.mapPoints(mClickCoords);
        middlex = mClickCoords[0];
        middley = mClickCoords[1];

        switch (newobj) {
            case CIRCLE:
                Objects.add(new ShapeCircle(this, middlex, middley, 100));
                break;
            case TRIANGLE:
                Objects.add(new ShapeTriangle(this, middlex, middley, this.getWidth() / 3, this.getHeight() / 3));
                break;
            case RECTANGLE:
                Objects.add(new ShapeRect(this, middlex, middley, middlex + this.getWidth() / 3, this.getHeight() / 3));
                break;
            case SQUARE:
                Objects.add(new ShapeRect(this, middlex, middley, 100));
                break;
            case TEXT:
                ObjectText newText = new ObjectText(this, middlex, middley, text, this.getWidth() / 6);
                select(newText);
                Objects.add(newText);
                break;
            case IMAGE:
                Objects.add(new ObjectPic(this, middlex, middley, imageMap));
                break;
            case LINER:
                currentLine = Object.OBJTYPE.LINER;
                addLine = 1;
                break;
        }
        text = "";
        invalidate();
    }


    public void select(Object newselect) {
        if (selectedobj != null) {
            selectedobj.setSelect(false);
            selectedobj = null;
        }
        selectedobj = newselect;
        selectedobj.setSelect(true);
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

        final float x = ev.getX();
        final float y = ev.getY();

        cX = mClickCoords[0];
        cY = mClickCoords[1];

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                MainActivity.text = "";
                startClickTime = Calendar.getInstance().getTimeInMillis();
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress()) {
                    final float dx = (x - mLastTouchX); // change in X
                    final float dy = (y - mLastTouchY); // change in Y

                    if (selectedobj != null) {
                        selectedobj.translate(dx / mScaleFactor, dy / mScaleFactor);
                    } else {
                        mPosX += dx;
                        mPosY += dy;
                    }

                }

                invalidate();

                mLastTouchX = x;
                mLastTouchY = y;

                break;

            }
            case MotionEvent.ACTION_UP: {

                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION && !mScaleDetector.isInProgress()) {
                    //selects object if tapped
                    //deselected all objects if nothing was tapped

                    boolean flag = false;
                    for (Object obj : Objects) {
                        if (obj.contains(new Point(cX, cY))) {
                            select(obj);
                            flag = true;
                        }
                    }
                    if (!flag) {
                        if (selectedobj != null) selectedobj.setSelect(false);
                        selectedobj = null;
                    }

                    if (addLine == 2) {
                        lp2 = new Point(cX, cY);
                        Objects.add(new ShapeLine(this, currentLine, lp1.getX(), lp1.getY(), lp2.getX(), lp2.getY()));
                        addLine = 0;
                    } else if (addLine == 1) {
                        lp1 = new Point(cX, cY);
                        addLine++;
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

