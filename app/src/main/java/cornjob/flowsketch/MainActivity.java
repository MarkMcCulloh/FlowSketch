package cornjob.flowsketch;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyCanvas canvas;
    public static MainActivity instance; // for "fill" and "stroke"

    /** For "fill" and "stroke" **/
    public static void setInstance(MainActivity instance) {
        MainActivity.instance = instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInstance(this); // for "fill" and "stroke"

        //setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        canvas = (MyCanvas) findViewById(R.id.drawablecanvas);
    }

    public void ColorPickerDialog()
    {
        int initialColor = Color.WHITE;
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener()
        {
            @Override
            public void onColorSelected(int color)
            {
                //colorAction.setBackgroundTintList(ColorStateList.valueOf(color));
            }
        });
        colorPickerDialog.show();
    }


    // Check screen orientation or screen rotate event here
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /**
        // Checks the orientation of the screen for landscape and portrait
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }**/
    }
    public void deleteObject(MenuItem v){canvas.delete();}
    public void clearCanvas(MenuItem v){canvas.reset();}
    public void insertRect(MenuItem v){canvas.setAddRect();}
    public void insertCircle(MenuItem v){canvas.setAddCircle();}
    public void insertLine(MenuItem v){canvas.setAddLine();}

}
