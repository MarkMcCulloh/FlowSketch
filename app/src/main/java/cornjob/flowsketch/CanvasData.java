package cornjob.flowsketch;

/**
 * Created by Nguyen on 11/16/2016.
 */
public class CanvasData {
    private int cid;
    private String canvasName;
    private String data;
    private String date;

    public CanvasData(int cid, String name, String data, String date){
        this.cid=cid;
        this.canvasName=name;
        this.data=data;
        this.date=date;
    }

    public CanvasData(int cid, String name, String date){
        this.cid=cid;
        this.canvasName=name;
        this.data=null;
        this.date=date;
    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCanvasName() {
        return canvasName;
    }

    public void setCanvasName(String canvasName) {
        this.canvasName = canvasName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
