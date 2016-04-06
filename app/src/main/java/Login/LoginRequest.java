package login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by derekhsieh on 4/3/16.
 * Information needed to sign up
 * that will be sent using retrofit
 */
public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(String user, String password) {
        this.username = user;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
