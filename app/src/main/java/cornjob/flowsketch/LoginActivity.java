package cornjob.flowsketch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "LoginActivity";
    private static final String URL_FOR_LOGIN = "http://flowsketchpi.duckdns.org:8080/sketch_flow/v1/login";
    ProgressDialog progressDialog;
    private EditText loginInputEmail, loginInputPassword;
    private Button btnlogin;
    private Button btnLinkSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginInputEmail = (EditText) findViewById(R.id.login_input_email);
        loginInputPassword = (EditText) findViewById(R.id.login_input_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnLinkSignup = (Button) findViewById(R.id.btn_link_signup);
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(loginInputEmail.getText().toString(),
                        loginInputPassword.getText().toString());
            }
        });

        btnLinkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);

            }
        });
    }

    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String name = jObj.getString("name");
                        String api_key=jObj.getString("apiKey");
                        String email= jObj.getString("email");
                        // Launch User activity
                        Toast.makeText(getApplicationContext(),
                                name + " "+ email +" "+ api_key, Toast.LENGTH_LONG).show();

                        User user= new User(name,email,api_key);

                        MainActivity.api_key=api_key;
                        MainActivity.username=name;
                        canvasFragment.log_in_status=true;

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                       /* Intent intent = new Intent(
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
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

