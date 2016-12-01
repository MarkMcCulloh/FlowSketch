package cornjob.flowsketch;

class ShapeCircle extends Object {

    private float circleRadius;

    ShapeCircle(MyCanvas mainCanvas, float x, float y, float r) {
        super(mainCanvas, x, y, OBJTYPE.CIRCLE);
        circleRadius = r;
    }

    ShapeCircle(MyCanvas mainCanvas, String inString) {
        super(mainCanvas, inString);
        String[] stuff = DECODE(inString);
        circleRadius = Float.parseFloat(stuff[10]);
    }

    @Override
    public boolean drawThis() {
        if (objSelect) {
            updateSelectBorder();
            objCanvas.canvas.drawCircle(objOrigin.getX(), objOrigin.getY(), circleRadius, selectBorder);
            objCanvas.canvas.drawCircle(objOrigin.getX(), objOrigin.getY(), circleRadius, selectBorder);
        }
        objCanvas.canvas.drawCircle(objOrigin.getX(), objOrigin.getY(), circleRadius, objPaintCurrent_Fill);
        objCanvas.canvas.drawCircle(objOrigin.getX(), objOrigin.getY(), circleRadius, objPaintCurrent_Stroke);
        return true;
    }

    @Override
    public boolean contains(Point test) {
        return Point.distance(objOrigin, test) <= circleRadius;
    }

    @Override
    public void translate(float xdis, float ydis) {
        objOrigin.move(xdis, ydis);
    }

    @Override
    public void scale(float factor) {
        circleRadius *= factor;
    }

    @Override
    public String encode() {
        return ENCODE(objType, objOrigin.getX(), objOrigin.getY(), objPaintCurrent_Fill.getColor(), objPaintCurrent_Stroke.getColor(), -1, -1, -1, -1, -1, circleRadius, "", "", -1, "");
    }

}

