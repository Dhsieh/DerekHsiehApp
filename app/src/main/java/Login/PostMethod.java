package login;

import login.Login;
import login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by derekhsieh on 4/3/16.
 */
public interface PostMethod {
    @POST("/{post}")
    public Call<LoginResponse> postResponse(@Path("post") String postUrl, @Body Login toSend);

}
