package Utils.RetroFit;

import friends.friendPage.RatingRequest;
import friends.friendPage.TopicRequest;
import friends.friendRequest.FriendRequestRequest;
import login.LoginRequest;
import login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import signUp.SignUpRequest;

/**
 * Created by derekhsieh on 4/5/16.
 * Interface for HTTP Post calls using retrofit
 */
public interface ToPost {

    //Post login information and return LoginResponse object
    @POST("/{post}")
    public Call<LoginResponse> postLogin(@Path("post") String postUrl, @Body LoginRequest toSend);

    //Post sign up information and return boolean if it was successful
    @POST("/{post}")
    public Call<Boolean> postSignUp(@Path("post") String url, @Body SignUpRequest toSend);

    //Post friend request response information and return boolean if it was successful
    @POST("/{post}")
    public Call<Boolean> postFriendRequestResponse(@Path("post") String url, @Body FriendRequestRequest toSend);

    @POST("/{post}")
    public Call<Boolean> postTopic(@Path("post") String url, @Body TopicRequest toSend);

    @POST("/{post}")
    public Call<Boolean> postRating(@Path("post") String url, @Body RatingRequest toSend);


}
