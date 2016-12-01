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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateDialogFragment extends DialogFragment {

    public static final String LOG_TAG = SaveDialogFragment.class.getSimpleName();
    public static String URL= "http://flowsketchpi.duckdns.org:8080/sketch_flow/v1/canvas/";
    String URL_UPDATE_CANVAS;
    //public static String URL_UPDATE_CANVAS;
    private ProgressDialog progressDialog;
    private String message;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        //loadFragment=(LoadFragment)this.getParentFragment();
        //get inflater from activity


         int id=AppSingleton.getInstance(getActivity()).getmSession().getCurrentCanvasID();
        String name=AppSingleton.getInstance(getActivity()).getmSession().getCurrent_canvas_name();
        URL_UPDATE_CANVAS=URL.concat(String.valueOf(id));

        final LayoutInflater inflater = getActivity().getLayoutInflater();

        HashMap<String, String> user = AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().getUserDetails();
        final String apiKey = user.get(SessionManager.API_KEY);
        //inflate custom view to dialog
        builder.setView(inflater.inflate(R.layout.fragment_update_dialog, null))
                .setTitle("Update")
                .setPositiveButton(R.string.save_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //get text from EditText
                        EditText editText = (EditText) getDialog().findViewById(R.id.edit_canvas_name);
                        String canvasName = editText.getText().toString();
                        postRequest(canvasName, apiKey, URL_UPDATE_CANVAS);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        dismiss();
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

    public UpdateDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_dialog, container, false);
    }


    public void postRequest(final String canvasName, final String apiKey,final String URL_UPDATE_CANVAS) {

        progressDialog.setMessage("Updating...");
        showDialog();

        final Context context=getActivity().getBaseContext();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL_UPDATE_CANVAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //message=response.toString();
                Log.i(LOG_TAG, response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");


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
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Updating Error: " + error.getMessage());
                String message= "Request timed out! Try again after a couple second!";
                if (error.getMessage() == null)
                    Toast.makeText(context,message, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context,error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
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

                MyCanvas.objectToString();
                //insert canvas arguments here
                params.put("name", canvasName);
                params.put("background", "gridview");
                params.put("data", CanvasData.data);
                params.put("thumbnail", "thumbnaillll");
                return params;
            }
        };
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

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
}
