package mainPage;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import Utils.Constants;
import derekhsieh.derekhsiehapp.R;
import friends.friendRequest.FriendRequestActivity;
import friends.friendList.FriendListActivity;


public class MainPageActivity extends ActionBarActivity{

    private String user;
    private int numFriendRequests;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Bundle extras = getIntent().getExtras();
        user = extras.getString(Constants.username);
        numFriendRequests = extras.getInt(Constants.friendRequests);
        ((Button) findViewById(R.id.NumFriendRequests)).setText(String.valueOf(numFriendRequests));

        StartNewHuntDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_navibar, menu);
        return true;
    }

    public void goToFriendRequests(View view) {
        Intent goToFriendRequestActivity = new Intent(this, FriendRequestActivity.class);
        goToFriendRequestActivity.putExtra("username", user);
        startActivity(goToFriendRequestActivity);
    }

    public void goToFriendList(View view) {
        Intent goToFriendListActivity = new Intent(this, FriendListActivity.class);
        goToFriendListActivity.putExtra("username", user);
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
