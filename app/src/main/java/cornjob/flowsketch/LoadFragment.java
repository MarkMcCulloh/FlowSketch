package cornjob.flowsketch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoadFragment extends Fragment {

    public LoadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_load, container, false);
        //User user= new User();
        //String name=user.setName();


        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Today Time Created");
        stringArrayList.add("Tomorrow Time Created");
        stringArrayList.add("Weds Time Created");
        stringArrayList.add("Thurs Time Created");
        stringArrayList.add("Fri Time Created");
        stringArrayList.add("Sat Time Created");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_canvas,R.id.list_item_canvas_textview, stringArrayList);

        //get listview
        ListView listView = (ListView) rootview.findViewById(R.id.listview_load);
        listView.setAdapter(stringArrayAdapter);
        return rootview;
    }

}
