package friends.friendRequest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Utils.RetroFit.RetroFitInterface;
import Utils.RetroFit.ToGet;
import derekhsieh.derekhsiehapp.MainPageActivity;
import derekhsieh.derekhsiehapp.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FriendRequestActivity extends ListActivity {
    private AsyncTask<String, String, List<String>> asyncTask;
    private String username;
    private List<String> friendRequests;
    private FriendRequestAdapter adapter;
    private int friendRequestCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        ToGet toGet = RetroFitInterface.createToGet();
        Call<List<String>> call = toGet.getListForUser("GetFriendRequests", username);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
                if (response.isSuccessful()) {
                    setContentView(R.layout.activity_friend_request);
                    friendRequests = response.body();
                    friendRequestCount = friendRequests.size();
                    if (friendRequests != null)
                        adapter = new FriendRequestAdapter(FriendRequestActivity.this, friendRequests, username);
                     else
                        adapter = new FriendRequestAdapter(FriendRequestActivity.this,
                                new ArrayList<String>(Arrays.asList(new String[]{"quiz", "help"})), username);

                    setListAdapter(adapter);
                } else {
                }

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent goToMainPage = new Intent(this, MainPageActivity.class);

    }


}
