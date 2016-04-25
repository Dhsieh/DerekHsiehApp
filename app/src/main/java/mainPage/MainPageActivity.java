package mainPage;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

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


public class MainPageActivity extends ListActivity {

    private String username;
    private int numFriendRequests;
    private Context context = this;
    private CurrentHuntAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Bundle extras = getIntent().getExtras();
        username = extras.getString(Constants.username);
        numFriendRequests = extras.getInt(Constants.friendRequests);

        ToGet toGet = RetroFitInterface.createToGet();
        Call<CurrentHuntResponse> call = toGet.getCurrentHuntResponse("GetCurrentHunts", username);
        call.enqueue(new Callback<CurrentHuntResponse>() {
            @Override
            public void onResponse(Call<CurrentHuntResponse> call, Response<CurrentHuntResponse> response) {
                if(response.isSuccessful()){
                    CurrentHuntResponse currentHuntResponse = response.body();
                    adapter = new CurrentHuntAdapter(context, currentHuntResponse, username);
                    setListAdapter(adapter);
                }else
                    Toast.makeText(context, Constants.errorMessage, Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<CurrentHuntResponse> call, Throwable t) {
                Toast.makeText(context, Constants.errorMessage, Toast.LENGTH_SHORT);
            }
        });



        ((Button) findViewById(R.id.NumFriendRequests)).setText(String.valueOf(numFriendRequests));

        //Start new hunt by sending a topic
        ((Button) findViewById(R.id.StartNewHunt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToGet toGet = RetroFitInterface.createToGet();
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
                        Toast.makeText(getApplicationContext(), Constants.errorMessage, Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    public void goToFriendRequests(View view) {
        Intent goToFriendRequestActivity = new Intent(this, FriendRequestActivity.class);
        goToFriendRequestActivity.putExtra("username", username);
        startActivity(goToFriendRequestActivity);
    }

    public void goToFriendList(View view) {
        Intent goToFriendListActivity = new Intent(this, FriendListActivity.class);
        goToFriendListActivity.putExtra("username", username);
        goToFriendListActivity.putExtra(Constants.friendRequests, numFriendRequests);
        startActivity(goToFriendListActivity);
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
}
