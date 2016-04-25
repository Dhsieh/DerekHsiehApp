package friends.friendRequest;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Utils.Constants;
import Utils.RetroFit.RetroFitInterface;
import Utils.RetroFit.ToGet;
import mainPage.MainPageActivity;
import derekhsieh.derekhsiehapp.R;
import retrofit2.Call;
import retrofit2.Callback;


public class FriendRequestActivity extends ListActivity {
    private AsyncTask<String, String, List<String>> asyncTask;
    private String username;
    private List<String> friendRequests;
    int friendRequestCount = 0;
    private FriendRequestAdapter adapter;

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

                    ButtonClickListener listener = new ButtonClickListener() {
                        @Override
                        public void onButtonClick( String friend) {
                            friendRequests.remove(friend);
                            adapter.notifyDataSetChanged();
                        }
                    };

                    if (friendRequests != null) {
                        adapter = new FriendRequestAdapter(FriendRequestActivity.this, friendRequests, username, listener);

                    }else {
                        adapter = new FriendRequestAdapter(FriendRequestActivity.this,
                                new ArrayList<String>(Arrays.asList(new String[]{"quiz", "help"})), username, listener);
                    }
                    setListAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), Constants.errorMessage, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Constants.errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("HTTP ERROR", t.getMessage());
            }
        });

    }

    public void onBackPressed(View view){
        Intent goToMainPage = new Intent(this, MainPageActivity.class);
        goToMainPage.putExtra(Constants.username, username);
        startActivity(goToMainPage);
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
        goToMainPage.putExtra(Constants.username, username);
        goToMainPage.putExtra(Constants.friendRequests, friendRequestCount);
    }

}
