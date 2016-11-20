package cornjob.flowsketch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoadFragment extends Fragment {

    private RecyclerView recyclerView;
    private CanvasDataAdapter adapter;
    private ArrayList<CanvasData> canvaslist;
    private ProgressDialog progressDialog;
    private static final String TAG = "LoadingActivity";
    private static final String URL= "http://flowsketchpi.duckdns.org:8080/sketch_flow/v1/canvas";
    SessionManager session;
    public LoadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_load, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        recyclerView=  (RecyclerView) rootview.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        session= new SessionManager(getActivity().getApplicationContext());

        //if(!AppSingleton.getInstance(getApplicationContext()).getmSession().isLoaded()){
        HashMap<String, String> user = session.getUserDetails();
        String api=user.get(SessionManager.API_KEY);
        getCanvases(api,rootview);
        //}


        return rootview;
    }

    private void getCanvases(final String api_key, final View rootview) {
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
                    boolean error = jObj.getBoolean("error");


                    if (!error) {

                        ArrayList<CanvasData> canvaslist= new ArrayList<>();

                        JSONArray canvas = jObj.getJSONArray("canvas");
                        CanvasData temp;
                        String name, data,date;
                        int cid;
                        for (int i=0; i<canvas.length(); i++){
                            JSONObject item=  canvas.getJSONObject(i);
                            cid=item.getInt("cid");
                            name=item.getString("name");
                            data=item.getString("data");
                            date=item.getString("createdAt");
                            temp= new CanvasData(cid,name,data,date);
                            canvaslist.add(temp);
                        }

                        Log.i("Array", Arrays.toString(canvaslist.toArray()));
                        AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().setCanvasList(canvaslist);

                        final ArrayList<CanvasData> canvaslis;
                        canvaslis=AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().getCanvasList();
                        Log.i("ArrayTest", Arrays.toString(canvaslis.toArray()));

                        canvaslist=AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().getCanvasList();
                        adapter = new CanvasDataAdapter(getActivity(), canvaslist);

                        recyclerView.setAdapter(adapter);


                        /*
                        Populate listview
                         */
                        //ArrayAdapter<CanvasData> canvasDataArrayAdapter = new ArrayAdapter<CanvasData>(getActivity(), R.layout.list_item_canvas,R.id.list_item_canvas_textview, canvaslis);


                    } else {

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Loading Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
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
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
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
