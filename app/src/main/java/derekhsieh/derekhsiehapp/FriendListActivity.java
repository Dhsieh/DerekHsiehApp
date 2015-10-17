package derekhsieh.derekhsiehapp;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import Adapters.FriendListAdapter;


public class FriendListActivity extends ListActivity {
    private List<String> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FriendListAdapter adapter;
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState != null) {
            this.friendList = savedInstanceState.getStringArrayList("friend_list");
        } else {
            this.friendList = extras.getStringArrayList("friendList");
        }
        setContentView(R.layout.activity_friend_list);
        TextView view = (TextView) findViewById(R.id.Username);
        view.setText(extras.getString("username"));
        if (friendList != null)
            adapter = new FriendListAdapter(this, friendList);
        else
            adapter = new FriendListAdapter(this, new ArrayList<>(Arrays.asList(new String[]{"test1", "test2"})));
        setListAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("friend_list", (ArrayList<String>) this.friendList);
    }
}
