package cornjob.flowsketch;

/**
 * Created by Khanh on 11/15/2016.
 */

public class User {
    private  static String name;
    private  static String email;
    private  static String api_key;
    private int uid;

    public User(String name, String email, String api){
        this.name=name;
        this.email=email;
        this.api_key=api;
        //this.uid=uid;

    }

    public User(){

    }
    public void setName(String name) {
        this.name = name;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
