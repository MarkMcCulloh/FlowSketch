package cornjob.flowsketch;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class LoadActivity extends AppCompatActivity {


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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


}
