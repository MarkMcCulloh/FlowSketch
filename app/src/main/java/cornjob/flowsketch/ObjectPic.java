package cornjob.flowsketch;

/**
 * Created by MarkM on 11/17/2016.
 */

public class ObjectPic extends Object {

    ObjectPic(MyCanvas mainCanvas, float x, float y, OBJTYPE type) {
        super(mainCanvas, x, y, type);
    }

    @Override
    public boolean drawThis() {
        return false;
    }

    @Override
    public boolean contains(Point test) {
        return false;
    }

    @Override
    public void translate(float xdis, float ydis) {

    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void scale(float factor) {

    }

    @Override
    public int getColor() {
        return -1;
    }

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
