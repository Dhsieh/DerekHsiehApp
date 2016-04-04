package friendRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by derekhsieh on 4/3/16.
 */
public interface GetMethod {
    @GET("/{get}")
    public Call<List<String>> getResponse(@Path("get") String url, @Query("username") String toSend);
}
