package cornjob.flowsketch;

/**
 * Created by john on 11/14/2016.
 */

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by john on 9/25/2016.
 */
public class MyCanvas extends View {
    Context context;

    private int i, lineCount = 0;
    private static float rectCordx , rectCordy, mark,x_mark,y_mark,x,y,x_move,y_move;
    public static boolean is_Marked, already_marked,remove_Object,inputText,object_Move,verify_Text =false;


    public static Bundle mMyAppsBundle = new Bundle();


    private EditText editxt;
    private static ArrayList<Rect> Rects = new ArrayList<>();
    private static ArrayList<RectF> Oval = new ArrayList<>();
    private static ArrayList<ShapeCircle> Circles = new ArrayList<>();
    private static ArrayList<ShapeLine>Lines = new ArrayList<>();
    private static ArrayList<object_Text>Text = new ArrayList<>();
    private static ArrayList<Rect>squares = new ArrayList<>();
    private static ArrayList<ShapeTriangle>tri = new ArrayList<>();

    private Point point1;
    private Point point2;

    private Paint linePaint;
    private Paint Circle_Paint;
    private Paint rectPaint;
    private Paint point_Paint;
    private Paint Marked;

    private String shape ="";
    private boolean addShape = false;

    public String text;

    private Path myPath;
    private Canvas canvas;
    private Bitmap bitmap;


    public MyCanvas(Context c, AttributeSet attributeSet )
    {
        super(c,attributeSet);
        context =c;

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

        if(addShape) {
            is_Marked = false;
            x_mark = 0.0f;
            y_mark = 0.0f;

            //Add New Rectangle
            if (shape == "Rect") {
                Rects.add(new Rect((int)rectCordx,(int)rectCordy,(int)rectCordx+300,(int)rectCordy+400));
                addShape = false;

                //Add New Circle
            } else if (shape == "Circle") {
                Circles.add(new ShapeCircle(rectCordx,rectCordy));
                addShape = false;
            }
            else if(shape == "Triangle")
            {
                tri.add(new ShapeTriangle(new Point(rectCordx,rectCordy)));
                addShape = false;
            }
            else if(shape == "Square")
            {
                squares.add(new Rect((int)rectCordx,(int)rectCordy,(int)rectCordx+400,(int)rectCordy+400));
                addShape = false;

            }
            else if(shape=="Oval")
            {
                Oval.add(new RectF((int)rectCordx,(int)rectCordy,(int)rectCordx+300,(int)rectCordy+400));
                addShape = false;
            }

            // Add New Line
            else if(shape == "Line") {
                if (i == 0){
                    point1 = new Point(rectCordx, rectCordy);
                    i++;
                    canvas.drawPoint(point1.getX(),point1.getY(),point_Paint);
                }
                else {
                    if(point1.getX() != rectCordx && i ==1)
                    {
                        point2 = new Point(rectCordx,rectCordy);
                        ShapeLine line = new ShapeLine(point1, point2);
                        Lines.add(line);
                        lineCount++;
                        i = 0;
                        addShape = false;

                    }

                }


            }


            //Traverse through the list of Shapes and draw on canvas if any are in list
            for(Rect rect : Rects)
                canvas.drawRect(rect,rectPaint);


            //Traverse through the list of circles and draw on canvas if any are in list
            for(ShapeCircle circ : Circles )
                canvas.drawCircle(circ.getX(),circ.getY(),circ.getRadius(),circ.getPaint());

            for(Rect square: squares)
                canvas.drawRect(square,rectPaint);

            for(RectF ovals: Oval)
                canvas.drawOval(ovals,rectPaint);

            for(ShapeTriangle triangle : tri)
                canvas.drawPath(triangle.getTri_Path(),rectPaint);

            for(ShapeLine line : Lines)
                canvas.drawLine(line.getPoint(0).getX(),line.getPoint(0).getY(),line.getPoint(1).getX(),line.getPoint(1).getY(),linePaint);


            // When only one point is created in order to create a line show user where the line will begin



        }

        // If user wants to drag an object to a different location
        else if(is_Marked && object_Move)
        {
            //Change Rect location
            for (Rect rect : Rects) {
                if (rect.contains((int) x_mark, (int) y_mark) && !already_marked) {
                    rect.left = (int)x_move;
                    rect.top = (int)y_move;
                    rect.bottom = (int)y_move+400;
                    rect.right = (int)x_move+300;
                    x_mark  = x_move;
                    y_mark = y_move;
                    canvas.drawRect(rect, Marked);

                    already_marked = true;
                } else {
                    canvas.drawRect(rect, rectPaint);
                }
            }

            for(Rect square : squares)
            {
                if (square.contains((int) x_mark, (int) y_mark) && !already_marked) {
                    square.left = (int)x_move;
                    square.top = (int)y_move;
                    square.bottom = (int)y_move + 400;
                    square.right = (int)x_move + 400;
                    x_mark  = x_move;
                    y_mark = y_move;
                    canvas.drawRect(square, Marked);

                    already_marked = true;
                } else {
                    canvas.drawRect(square, rectPaint);
                }
            }
            for(RectF ovals:Oval)
            {
                if(ovals.contains((int) x_mark, (int) y_mark) && !already_marked)
                {
                    ovals.left = (int)x_move;
                    ovals.top = (int)y_move;
                    ovals.bottom = (int)y_move + 400;
                    ovals.right = (int)x_move + 300;
                    x_mark = x_move;
                    x_mark = y_move;
                    canvas.drawOval(ovals,Marked);
                    already_marked =true;
                }
                else{
                    canvas.drawOval(ovals,rectPaint);
                }
            }

            /** FIX NEEDED HERE!!!
             * - I think something might be wrong here, since we have a bug when you place a circle
             * and then from here on out, if you ever press the screen, it immediately moves the
             * circle to where you pressed **/
            // Change Circle location
            /**
            for (ShapeCircle circ : Circles) {
                if ((x_mark - circ.getX()*circ.getX() + y_mark - circ.getY()*circ.getY())<(circ.getRadius()*circ.getRadius()) && !already_marked) {
                    already_marked = true;
                    x_mark  = x_move;
                    y_mark = y_move;
                    circ.setX(x_move);
                    circ.setY(y_move);
                    canvas.drawCircle(circ.getX(), circ.getY(), circ.getRadius(), Marked);
                }
                else
                {
                    canvas.drawCircle(circ.getX(), circ.getY(), circ.getRadius(),circ.getPaint());}
            } **/
            object_Move = false;
            already_marked = false;
            for(ShapeLine line : Lines)
            {
                canvas.drawLine(line.getPoint(0).getX(),line.getPoint(0).getY(),line.getPoint(1).getX(),line.getPoint(1).getY(),rectPaint);
            }
        }

        // If user point on object it will be declared as mark.
        else if(is_Marked) {
            for (Rect rect : Rects) {
                if (rect.contains((int) x_mark, (int) y_mark) && !already_marked) {

                    canvas.drawRect(rect, Marked);
                }
                else {
                    canvas.drawRect(rect, rectPaint);
                }
            }
            for (Rect square : squares) {
                if (square.contains((int) x_mark, (int) y_mark) && !already_marked) {

                    canvas.drawRect(square, Marked);
                }
                else {
                    canvas.drawRect(square, rectPaint);
                }
            }
            for(RectF ovals: Oval)
            {
                if (ovals.contains((int) x_mark, (int) y_mark) && !already_marked) {

                    canvas.drawOval(ovals, Marked);
                }
                else {
                    canvas.drawOval(ovals, rectPaint);
                }
            }
            for (ShapeCircle circ : Circles) {
                if ((x_mark - circ.getX()*circ.getX() + y_mark - circ.getY()*circ.getY())<(circ.getRadius()*circ.getRadius()) && !already_marked) {
                    already_marked = true;
                    canvas.drawCircle(circ.getX(), circ.getY(), circ.getRadius(), Marked);
                }
                else
                {
                    canvas.drawCircle(circ.getX(), circ.getY(), circ.getRadius(), circ.getPaint());}

            }
            already_marked = false;
        }

        // Draw all objects the were created on the canvas
        else if(inputText)
        {

        }
        else
        {
            for(Rect rect : Rects)
                canvas.drawRect(rect, rectPaint);

            for(Rect square : squares)
                canvas.drawRect(square,rectPaint);

            for(ShapeCircle circ : Circles)
                canvas.drawCircle(circ.getX(), circ.getY(), circ.getRadius(),circ.getPaint());

            for(ShapeTriangle triangle: tri)
                canvas.drawPath(triangle.getTri_Path(),rectPaint);

            for(RectF ovals:Oval)
                canvas.drawOval(ovals,rectPaint);

            for(ShapeLine line : Lines)
                canvas.drawLine(line.getPoint(0).getX(),line.getPoint(0).getY(),line.getPoint(1).getX(),line.getPoint(1).getY(),rectPaint);
        }
    }

    /** Delete objects on canvas
     * - This is called from "canvasFragment.onOptionsItemSelected()" **/
    public static void DeleteObject()
    {
        // Check all squares for the "marked" one
        for(Rect square : squares)
            if(square.contains((int) x_mark, (int) y_mark) ) {
                square.setEmpty(); // Delete the highlighted square
                return;
            }

        // Check all rectangles for the "marked" one
        for(Rect rect : Rects)
            if(rect.contains((int) x_mark, (int) y_mark) ) {
                rect.setEmpty(); // Delete the highlighted rectangle
                return;
            }

        // Check all circles for the "marked" one
        for(ShapeCircle circ : Circles)
            if((x_mark - circ.getX()*circ.getX() + y_mark - circ.getY()*circ.getY())<(circ.getRadius()*circ.getRadius())) {
                circ.setColor(0x00000000); // Delete the highlighted circle
                return;
            }


        /** FIX NEEDED HERE!!!
         * - Dimensions of oval: oval.getX(), oval.getY(), don't know what to use **/
        // Check all ovals for the "marked" one
        /**
        for(RectF oval : Oval) {
            if((x_mark - oval.getX()*oval.getX() + y_mark - oval.getY()*oval.getY())<(oval.getRadius()*oval.getRadius())) {
                oval.setColor(0x00000000);
            }
        } **/

        /** FIX NEEDED HERE!!!
         * - Dimensions of triangle **/
        // Check all triangles for the "marked" one
        /**
        for(ShapeTriangle triangle : Triangles) {
            if((x_mark - circ.getX()*circ.getX() + y_mark - circ.getY()*circ.getY())<(circ.getRadius()*circ.getRadius())) {
                circ.setColor(0x00000000);
            }
        } **/

        /** FIX NEEDED HERE!!!
         * - Dimensions of diamond **/
        // Check all diamonds for the "marked" one
        /**
         for(Diamonds diamond : Diamonds) {
            if((x_mark - circ.getX()*circ.getX() + y_mark - circ.getY()*circ.getY())<(circ.getRadius()*circ.getRadius())) {
                circ.setColor(0x00000000);
            }
         } **/
    }


    //add different Objects, text and remove
    public void setText()
    {
        inputText = true;
    }
    public void reset()
    {
        squares.clear();
        Rects.clear();
        Circles.clear();
        Lines.clear();
        tri.clear();
        Oval.clear();
        shape = "";
        addShape = false;
        i = 0;
        invalidate();
    }
    public void setAddRect()
    {
        addShape = true;
        shape ="Rect";
    }
    public void setSquare()
    {
        addShape =true;
        shape ="Square";
    }
    public void setAddCircle()
    {
        addShape = true;
        shape ="Circle";
    }
    public void setAddLine()
    {
        addShape = true;
        shape = "Line";
    }
    public void setTriangle()
    {
        addShape =true;
        shape = "Triangle";
    }
    public void setOval()
    {
        addShape = true;
        shape = "Oval";
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








    public boolean onTouchEvent(MotionEvent event)
    {
        float x= event.getX();
        float y =event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(addShape)
                {
                    rectCordx = x;
                    rectCordy = y;

                }
                // If the user is not creating a new shape than we will assume that a user want to modify an object

                else
                {
                    x_mark = x;
                    y_mark = y;
                    if(is_Marked) {
                        is_Marked = false;
                        already_marked = false;
                    }
                    else
                    {
                        is_Marked = true;
                    }

                }
                break;

            case MotionEvent.ACTION_UP:
            {

            }

            case MotionEvent.ACTION_MOVE:
            {
                object_Move = true;

                x_move = x;
                y_move = y;


            }
            break;

        }
        invalidate();
        return true;

    }


}

