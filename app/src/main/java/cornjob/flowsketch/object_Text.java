package cornjob.flowsketch;

/**
 * Created by john on 11/15/2016.
 */
public class object_Text {
    private String text;
    private float startx;
    private float starty;

    public object_Text(float x, float y)
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
}
