package login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by derekhsieh on 4/3/16.
 */
public class Login {

    private String username;
    private String password;

    public Login(String user, String password) {
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

//    public interface PostLogin{
//        @POST("/{post}")
//        public Call<String> postResponse(@Path("post") String postUrl, @Body Login toSend);
//    }
}
