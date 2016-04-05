package signUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by derekhsieh on 4/4/16.
 */
public interface PostMethod {
    @POST("/{post}")
    public Call<Boolean> getResponse(@Path("post") String url, @Body SignUpRequest toSend);
}
