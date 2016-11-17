package cornjob.flowsketch;

import android.graphics.Canvas;

/**
 * Created by MarkM on 11/17/2016.
 */

public class ObjectPic extends Object {
    @Override
    public boolean drawThis(Canvas objCanvas) {
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
}
