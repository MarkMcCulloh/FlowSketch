package cornjob.flowsketch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoadActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private static final String TAG = "LoginActivity";
    private static final String URL= "http://flowsketchpi.duckdns.org:8080/sketch_flow/v1/canvas";
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        session= new SessionManager(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HashMap<String, String> user = session.getUserDetails();

        String api=user.get(SessionManager.API_KEY);
        getCanvases(api);
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

    private void getCanvases(final String api_key) {
        // Tag used to cancel the request
        String cancel_req_tag = "load";
        progressDialog.setMessage("Loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Load Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);

                    /* Example response
                    {
                    "Getting all canvas from user": 2,
                    "error": false,
                    "canvas": [
                      {
                            "cid": 5,
                            "name": "231",
                            "data": "object, circle, color, red, outer, blue, xpos, 213, ypos, 31223, rotate, 0",
                            "background": "hahahha",
                            "thumbnail": "hehe",
                            "createdAt": "2016-10-26 14:03:26"
                       },
                       {
                            "cid": 6,
                            "name": "312",
                            "data": "object, circle, color, red, outer, blue, xpos, 213, ypos, 31223, rotate, 0, object, triangle,color, greeb, outer, blue, xpos, 312, ypos, 12223, rotate, 0",
                            "background": "0",
                            "thumbnail": "123414512312",
                            "createdAt": "2016-10-26 14:07:28"
                       }
                    ],
                    }
                      */
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        //MainActivity.canvasList= new ArrayList();

                        /*Intent intent = new Intent(
                                LoginActivity.this,
                                UserActivity.class);
                        intent.putExtra("name", user);
                        startActivity(intent);
                        finish();*/
                    } else {

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("authorization",api_key);
                return headers;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
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
