package friends;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by derekhsieh on 4/3/16.
 */
public interface GetMethod {
    @GET("/{get}")
    public Call<List<String>> postResponse(@Path("get") String postUrl, @Query("username") String toSend);

}
