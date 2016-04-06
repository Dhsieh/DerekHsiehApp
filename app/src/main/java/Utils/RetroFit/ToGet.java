package Utils.RetroFit;

import java.util.List;

import friends.friendPage.FriendPageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by derekhsieh on 4/5/16.
 * Interface for HTTP Get calls using retrofit
 */
public interface ToGet {

    //Specifically gets information for a Friend Page
    @GET("/{get}")
    public Call<FriendPageResponse> getFriendPageResponse(@Path("get") String url, @Query("username") String username, @Query("friend") String friend);

    //Gets a list for a user, differes based on what the URL is
    @GET("/{get}")
    public Call<List<String>> getListForUser(@Path("get") String url, @Query("username") String toSend);

}
