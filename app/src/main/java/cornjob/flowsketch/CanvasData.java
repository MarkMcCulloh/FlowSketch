package cornjob.flowsketch;

import java.io.Serializable;

/**
 * Created by Nguyen on 11/16/2016.
 * Data to get out of a row in canvas table
 */
public class CanvasData implements Serializable {
    private int cid;
    private String canvasName;
    public static String data = "";
    private String data_temp;
    private String date;

    public CanvasData(int cid, String name, String data, String date) {
        this.cid = cid;
        this.canvasName = name;
        this.data_temp = data;
        this.date = date;
    }

    public CanvasData(int cid, String name, String date) {
        this.cid = cid;
        this.canvasName = name;
        this.data_temp = null;
        this.date = date;
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
        CanvasData.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String toString() {
        return "Title: " + canvasName + "\nDate: " + date;
    }

    public String getData_temp() {
        return data_temp;
    }

    public void setData_temp(String data_temp) {
        this.data_temp = data_temp;
    }
}
