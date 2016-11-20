package cornjob.flowsketch;

/**
 * Created by john on 11/15/2016.
 */
public class ObjectText {
    private String text;
    private float startx;
    private float starty;

    public ObjectText(float x, float y)
    {
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
    public float getX()
    {
        return  this.startx;
    }
    public float getY()
    {
        return this.starty;
    }
}
