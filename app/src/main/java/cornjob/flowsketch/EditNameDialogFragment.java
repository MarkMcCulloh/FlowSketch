package cornjob.flowsketch;


//import android.app.AlertDialog;

//import android.app.Dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditNameDialogFragment extends DialogFragment{

    public static final String LOG_TAG = EditNameDialogFragment.class.getSimpleName();
    public static String URL = "http://flowsketchpi.duckdns.org:8080/sketch_flow/v1/canvasTitle/";
    public static String URL_UPDATE_CANVAS;
    private ProgressDialog progressDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle mArgs = getArguments();
        String id = mArgs.getString("canvasId");
        URL_UPDATE_CANVAS = URL.concat(id);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        //get inflater from activity
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        HashMap<String, String> user = AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().getUserDetails();
        final String apiKey = user.get(SessionManager.API_KEY);
        //inflate custom view to dialog
        builder.setView(inflater.inflate(R.layout.fragment_save_dialog, null))
                .setTitle(R.string.save_canvas_dialog)
                .setPositiveButton(R.string.save_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //get text from EditText
                        EditText editText = (EditText) getDialog().findViewById(R.id.edit_canvas_name);
                        String canvasName = editText.getText().toString();
                        postRequest(canvasName, apiKey, URL_UPDATE_CANVAS);
                        //Toast.makeText(getActivity(), canvasName + URL_UPDATE_CANVAS, Toast.LENGTH_SHORT).show();
                        Log.i(LOG_TAG, URL_UPDATE_CANVAS );
                    }
                })
                .setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

        //create and return dialog
        return builder.create();
    }

    public EditNameDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_dialog, container, false);
    }


    public void postRequest(final String name, final String apiKey, final String URL_UPDATE_CANVAS) {
        // Toast.makeText(getActivity(), "Save Canvas", Toast.LENGTH_SHORT).show();

        progressDialog.setMessage("Saving...");
        showDialog();

        final Context context=getActivity();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL_UPDATE_CANVAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(LOG_TAG, response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String message;

                    if (!error) {



                        message = jObj.getString("message");
                        Toast.makeText(context,message, Toast.LENGTH_LONG).show();


                    } else {

                        message = jObj.getString("message");
                        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Edit Error: " + error.getMessage());
                String message= "Request timed out! Try again after a couple second!";
                if (error.getMessage() == null)
                     Toast.makeText(context,message, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context,error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
                //hideDialog();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                //insert headers here
                //headers.put("authorization","06014f0c3c064fbe020009519300df31");
                headers.put("authorization", apiKey);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                //insert canvas arguments here
                params.put("name", name);
                return params;
            }
        };

        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest, "Save");
        //RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //requestQueue.add(stringRequest);
    }
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public static class DismissDialog extends DialogFragment {
        private DialogInterface.OnDismissListener onDismissListener;

        public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (onDismissListener != null) {
                onDismissListener.onDismiss(dialog);
            }
        }
    }
}
