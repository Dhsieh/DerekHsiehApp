package mainPage;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import Utils.Constants;
import Utils.RetroFit.RetroFitInterface;
import Utils.RetroFit.ToGet;
import Utils.RetroFit.ToPost;
import derekhsieh.derekhsiehapp.R;
import friends.friendPage.NewTopicDialog;
import friends.friendPage.TopicRequest;
import friends.friendRequest.FriendRequestActivity;
import friends.friendList.FriendListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPageActivity extends AppCompatActivity {


    private String username;
    private int numFriendRequests;
    private Context context = this;
    private CurrentHuntAdapter adapter;
    private Toolbar myToolbar;
    private BottomBar mBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);
        Bundle extras = getIntent().getExtras();
        username = extras.getString(Constants.username);
        numFriendRequests = extras.getInt(Constants.friendRequests);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noTabletGoodness();
        final ToGet toGet = RetroFitInterface.createToGet();
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.StartNewHunt) {
                    Call<List<String>> call = toGet.getListForUser("GetFriendsToPlayWith", username);
                    call.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if (response.isSuccessful()) {
                                List<String> friends = response.body();
                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainPageActivity.this, R.layout.friend_list, R.id.FriendName, friends);
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                dialogBuilder.setTitle("Pick a Friend");
                                dialogBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String friend = arrayAdapter.getItem(which);
                                        NewTopicDialog newTopicDialog = new NewTopicDialog(context);
                                        newTopicDialog.show();
                                        newTopicDialog.setDialogResult(new NewTopicDialog.OnNewTopicDialogResult() {
                                            @Override
                                            public void finish(String result) {
                                                ToPost toPost = RetroFitInterface.createToPost();
                                                Call<Boolean> call = toPost.postTopic("AddTopic", new TopicRequest(username, friend, result, System.currentTimeMillis()));
                                                call.enqueue(new Callback<Boolean>() {
                                                    @Override
                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                        if (response.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "SENT NEW TOPIC. YISSSS!!! ", Toast.LENGTH_SHORT).show();
                                                        } else
                                                            Toast.makeText(getApplicationContext(), "Could not send topic ", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                                        Toast.makeText(getApplicationContext(), "Could not send topic ", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                AlertDialog startHuntDialog = dialogBuilder.create();
                                startHuntDialog.show();

                            } else {
                                Toast.makeText(getApplicationContext(), Constants.errorMessage, Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {

                        }
                    });
                }

                if(menuItemId == R.id.friendList){
                    Intent goToFriendListActivity = new Intent(context, FriendListActivity.class);
                    goToFriendListActivity.putExtra(Constants.username, username);
                    goToFriendListActivity.putExtra(Constants.friendRequests, numFriendRequests);
                    startActivity(goToFriendListActivity);
                }

                if(menuItemId == R.id.Settings){
                    //go to settings page
                }


            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if(menuItemId == R.id.Home){
                    Intent goToHome = new Intent(context, MainPageActivity.class);
                    goToHome.putExtra(Constants.username, username);
                    goToHome.putExtra(Constants.friendRequests, numFriendRequests);
                    startActivity(goToHome);
                }


            }
        });

        mBottomBar.mapColorForTab(0, "#88BED7");
        mBottomBar.mapColorForTab(1, "#88BED7");
        mBottomBar.mapColorForTab(2, "#88BED7");
        BottomBarBadge unreadMessages = mBottomBar.makeBadgeForTabAt(2, "#FF0000", numFriendRequests);
        unreadMessages.show();
        mBottomBar.mapColorForTab(3, "#88BED7");




        Call<CurrentHuntResponse> call = toGet.getCurrentHuntResponse("GetCurrentHunts", username);
        call.enqueue(new Callback<CurrentHuntResponse>() {
            @Override
            public void onResponse(Call<CurrentHuntResponse> call, Response<CurrentHuntResponse> response) {
                if (response.isSuccessful()) {
                    CurrentHuntResponse currentHuntResponse = response.body();
                    adapter = new CurrentHuntAdapter(context, currentHuntResponse, username);
                    ListView list = (ListView) findViewById(R.id.list);
                    list.setAdapter(adapter);
                } else
                    Toast.makeText(context, Constants.errorMessage, Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<CurrentHuntResponse> call, Throwable t) {
                Toast.makeText(context, Constants.errorMessage, Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_navibar, menu);
        return true;
    }

//    public void goToFriendRequests(View view) {
//        Intent goToFriendRequestActivity = new Intent(this, FriendRequestActivity.class);
//        goToFriendRequestActivity.putExtra("username", username);
//        startActivity(goToFriendRequestActivity);
//    }

//    public void goToFriendList(View view) {
//        Intent goToFriendListActivity = new Intent(this, FriendListActivity.class);
//        goToFriendListActivity.putExtra("username", username);
//        goToFriendListActivity.putExtra(Constants.friendRequests, numFriendRequests);
//        startActivity(goToFriendListActivity);
//    }

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

    public void goToFriendRequests(MenuItem item) {
    }
}
