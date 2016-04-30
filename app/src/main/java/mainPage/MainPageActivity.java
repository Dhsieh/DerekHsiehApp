package mainPage;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import Utils.Constants;
import derekhsieh.derekhsiehapp.R;
import friends.friendRequest.FriendRequestActivity;
import friends.friendList.FriendListActivity;


public class MainPageActivity extends AppCompatActivity{

    private String user;
    private int numFriendRequests;
    private Toolbar myToolbar;
    private BottomBar mBottomBar;


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

        //Implements BottomBar App
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.StartNewHunt) {

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.StartNewHunt) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

        mBottomBar.mapColorForTab(0, "#8C001A");
        mBottomBar.mapColorForTab(1, "#FF9800");
        mBottomBar.mapColorForTab(2, "#8C001A");
        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");

        BottomBarBadge unreadMessages = mBottomBar.makeBadgeForTabAt(0, "#88BED7", 13);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
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

    public void goToFriendRequests(MenuItem item) {
    }
}
