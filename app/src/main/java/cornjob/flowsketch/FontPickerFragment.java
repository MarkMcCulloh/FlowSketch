package cornjob.flowsketch;

/**
 * Created by Austin on 11/21/2016.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FontPickerFragment extends DialogFragment
{

    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        final String[] items = {"Calibri", "Magenta", "Times New Roman", "Agency FB", "Comic Sans MS"};

        // Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle("FONT");
        builder.setItems(R.array.fonts_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });


        // Get the AlertDialog from create() and show the dialog

        return builder.create();
    }
}
