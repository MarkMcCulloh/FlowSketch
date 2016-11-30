package cornjob.flowsketch;

/**
 * Created by Khanh on 11/15/2016.
 */

public class User {
    private static String name;
    private static String email;
    private static String api_key;
    private int uid;

    public User(String name, String email, String api) {
        User.name = name;
        User.email = email;
        api_key = api;
        //this.uid=uid;

    }

    public User() {

    }

    public void setName(String name) {
        User.name = name;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        User.api_key = api_key;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        User.email = email;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
