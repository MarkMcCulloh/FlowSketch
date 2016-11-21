package cornjob.flowsketch;

/**
 * Created by john on 11/15/2016.
 */
public class objectText extends Object {
    private String text = new String();
    private float startx;
    private float starty;

    public objectText(MyCanvas maincanvas,float x, float y,OBJTYPE text)
    {
        super(maincanvas,x,y,text);
        startx = x;
        starty =y;

    }
    public void setText(String text)
    {
        this.text = text;

    }
    public String getText()
    {
        return this.text;
    }
    public  boolean drawThis(){return true;}

    public  boolean contains(Point test){return false;}

    public  void translate(float xdis, float ydis){}

    public  void rotate(float angle){}

    public  void scale(float factor){}

    @Override
    public int getColor() {
        return objPaintRegular.getColor();
    }

    //please fix all these
    @Override
    public float getXPos() {
        return 0;
    }

    @Override
    public float getYPos() {
        return 0;
    }

    @Override
    public float getLength() {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getRadius() {
        return 0;
    }
}
