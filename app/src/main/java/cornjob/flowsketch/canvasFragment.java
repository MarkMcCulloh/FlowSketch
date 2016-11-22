package cornjob.flowsketch;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static cornjob.flowsketch.SaveDialogFragment.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class canvasFragment extends Fragment {

    // Required for text font selection
    public static Typeface face;

    public static boolean log_in_status = false; // Boolean whether user is logged in or not
                                          // Options menu changes depending on this variable
                                          // NOTE: Change this variable to 'true' when user
                                          // logs in
   // SessionManager session;
    public canvasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //session= new SessionManager(getActivity().getApplicationContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        /** Change 'options' based on whether user is logged in or out **/
        if(!AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().isLoggedIn())
            inflater.inflate(R.menu.menu_main_logged_out, menu); //
        else
            inflater.inflate(R.menu.menu_main_logged_in, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.fill_action)
        {   MainActivity.fillOrStroke = "Fill";
            MainActivity.instance.ColorPickerDialog();}
        if(id == R.id.stroke_action)
        {   MainActivity.fillOrStroke="Stroke";
            MainActivity.instance.ColorPickerDialog();}

        /* Go to login activity */
        if(id == R.id.login_action)
        {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        if(id ==R.id.log_out_action)
        {
            AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().createLogout();
            Toast.makeText(getActivity(),"You have logged out!", Toast.LENGTH_SHORT).show();
        }
        /* Import image from gallery */
        if(id == R.id.image_item)
        {
            Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, 0);
        }


        // Set the Typeface 'face' to Calibri font
        if(id == R.id.font_calibri) {
            face = Typeface.createFromAsset(getContext().getAssets(), "calibri.ttf");
            MainActivity.instance.insertText(item);
        }
        else if(id == R.id.font_magenta) {
            face = Typeface.createFromAsset(getContext().getAssets(), "magenta.ttf");
            MainActivity.instance.insertText(item);
        }
        else if(id == R.id.font_times_new_roman) {
            face = Typeface.createFromAsset(getContext().getAssets(), "times_new_roman.ttf");
            MainActivity.instance.insertText(item);
        }
        else if(id == R.id.font_agency_fb) {
            face = Typeface.createFromAsset(getContext().getAssets(), "agency-fb.ttf");
            MainActivity.instance.insertText(item);
        }
        else if(id == R.id.font_comic_sans_ms) {
            face = Typeface.createFromAsset(getContext().getAssets(), "comicsans_ms.ttf");
            MainActivity.instance.insertText(item);
        }


        if(id == R.id.square_item){
            Toast.makeText(getActivity(), "Square Clicked", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.circle_item){
            Toast.makeText(getActivity(), "Circle Clicked", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.triangle_item){
            Toast.makeText(getActivity(), "Triangle Clicked", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.rectangle_item){
            Toast.makeText(getActivity(), "Rectangle Clicked", Toast.LENGTH_SHORT).show();
        }
        //overflow options
        else if(id == R.id.save_canvas_action){

            //display popup dialog
            new SaveDialogFragment().show(getFragmentManager(),LOG_TAG);
        }
        else if(id == R.id.load_canvas_action){
            Intent intent = new Intent(getActivity(), LoadActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}