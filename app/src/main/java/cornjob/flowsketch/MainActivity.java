
package cornjob.flowsketch;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public final String LOG_TAG = MainActivity.class.getSimpleName();
    private MyCanvas canvas;
    public static MainActivity instance; // for "fill" and "stroke"
    public static String api_key="";
    public static String username="";

    /* For "fill" and "stroke" */
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


    /* Color Picker Wheel */
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

    /* Grab image from phone gallery */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            cursor.close();

            // String picturePath contains the path of selected Image
        }
    }

    /* Check screen orientation or screen rotate event here */
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

    public void insertSquare(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.SQUARE);
    }

    public void insertRect(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.RECTANGLE);
    }

    public void insertCircle(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.CIRCLE);
    }

    public void insertLine(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.LINE);
    }

    public void insertTriangle(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.TRIANGLE);
    }

    public void insertText(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.TEXT);
    }

    public void insertImage(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.IMAGE);
    }

}
