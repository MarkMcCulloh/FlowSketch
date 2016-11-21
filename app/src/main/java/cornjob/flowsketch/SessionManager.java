package cornjob.flowsketch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nguyen on 11/17/2016.
 * This class uses SharedPreferences class to share the data within the app
 * The data will still remain after the app restarts
 * The class is called in AppSingleton to make sure there is only one instance at a time
 * To call use:
 *            AppSingleton.getInstance(getApplicationContext()).getmSession().[some method];
 * In case it is called inside a fragment:
 *  *         AppSingleton.getInstance(getActivity().getApplicationContext()).getmSession().[some method];


 */
public class SessionManager {
    SharedPreferences pref;
    private static SessionManager sInstance = null;

    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "CornJobPref";
    public static final String EMAIL="email";
    public static final String API_KEY="api";
    private static final String IS_LOGIN="login";
    public static final String USERNAME="user name";

    private ArrayList<CanvasData> listwriter;
    public static final String MY_LIST = "my_list";
    private static final Type LIST_TYPE = new TypeToken<CanvasData>() {}.getType();

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email, String apiKey){

        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(USERNAME, name);

        // Storing email in pref
        editor.putString(EMAIL, email);

        editor.putString(API_KEY, apiKey);

        // commit changes
        editor.commit();
    }

    /**
     * Set canvasData array as shared ref.
     * @param listwriter
     */
    public void setCanvasList(ArrayList<CanvasData> listwriter) {
        this.listwriter = new ArrayList<>(listwriter);

        Gson gson= new Gson();
        editor.putString(MY_LIST, gson.toJson(listwriter));
        editor.commit();
    }

    /**
     * get the CanvasData array
     * @return
     */
    public ArrayList<CanvasData> getCanvasList() {
        if (listwriter == null) {
            listwriter = new Gson().fromJson(pref.getString(MY_LIST, null), LIST_TYPE);
            if(listwriter == null){
                listwriter = new ArrayList<>();
           }
        }
        return listwriter;
    }

    /**
     * remove 1 element in the array
     * @param data
     */
    public void removeCanvas(CanvasData data) {
        if (listwriter != null) {
            listwriter.remove(data);
            setCanvasList(listwriter);
        }
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(USERNAME, pref.getString(USERNAME, null));

        // user email id
        user.put(EMAIL, pref.getString(EMAIL, null));

        user.put(API_KEY, pref.getString(API_KEY, null));
        // return user
        return user;
    }


    /**
     * Create logout session
     * */
    public void createLogout(){
        // Clearing all data from Shared Preferences
        listwriter=null;
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /*
        Check if the data has already loaded
     */
    public boolean isLoaded(){
        return listwriter!=null;
    }
}
