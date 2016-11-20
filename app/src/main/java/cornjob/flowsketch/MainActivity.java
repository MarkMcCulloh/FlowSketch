package cornjob.flowsketch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private MyCanvas canvas;
    private static String text ="";
    private Point pt;
    private static boolean addText;
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
                canvas.setColor(color);//colorAction.setBackgroundTintList(ColorStateList.valueOf(color));
            }
        });
        colorPickerDialog.show();
    }

    public void loadImagefromGallery(MenuItem view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_OK);
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
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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


    public void insertText(MenuItem v){
        text="";
        addText = true;
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        pt = new Point(0,0);

    }
    public boolean onKeyDown(int keycode,KeyEvent e)
    {
        text += (char)e.getUnicodeChar();
        if(text != null) {
            canvas.setText(text,pt);
            canvas.addObject(Object.OBJTYPE.TEXT);
        }
        return super.onKeyDown(keycode,e);
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

    public void insertImage(MenuItem v) {
        canvas.addObject(Object.OBJTYPE.IMAGE);
    }

}
