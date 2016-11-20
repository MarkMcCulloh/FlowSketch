package cornjob.flowsketch;


//import android.app.AlertDialog;

//import android.app.Dialog;

import android.app.Dialog;
import android.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveDialogFragment extends DialogFragment {

    public static final String LOG_TAG = SaveDialogFragment.class.getSimpleName();
    public static String URL_SAVE_CANVAS = "http://flowsketchpi.duckdns.org:8080/sketch_flow/v1/canvas";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //get inflater from activity
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        HashMap<String,String> user=AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().getUserDetails();
        final String apiKey=user.get(SessionManager.API_KEY);
        //inflate custom view to dialog
        builder.setView(inflater.inflate(R.layout.fragment_save_dialog, null))
                .setTitle(R.string.save_canvas_dialog)
                .setPositiveButton(R.string.save_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //get text from EditText
                        EditText editText = (EditText) getDialog().findViewById(R.id.edit_canvas_name);
                        String canvasName = editText.getText().toString();
                        postRequest(canvasName,apiKey);
                        Toast.makeText(getActivity(),canvasName, Toast.LENGTH_SHORT).show();
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

    public SaveDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_dialog, container, false);
    }


    public void postRequest(final String canvasName, final String apiKey){
        Toast.makeText(getActivity(), "Save Canvas", Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_CANVAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(LOG_TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //06014f0c3c064fbe020009519300df31
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                //insert headers here
                //headers.put("authorization","06014f0c3c064fbe020009519300df31");
                headers.put("authorization",apiKey);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                //insert canvas arguments here
                params.put("name", canvasName);
                params.put("background", "gridview");
                params.put("data", "obj1 obj2");
                params.put("thumbnail", "thumbnaillll");
                return params;
            }
        };

        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest,"Save");
        //RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //requestQueue.add(stringRequest);
    }
}
