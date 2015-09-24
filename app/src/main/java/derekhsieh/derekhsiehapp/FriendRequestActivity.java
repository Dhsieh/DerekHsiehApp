package derekhsieh.derekhsiehapp;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import Adapters.FriendRequestAdapter;
import AsyncTaskRunners.FriendRequestAsyncTaskRunner;
import Objects.FriendRequests;


public class FriendRequestActivity extends ListActivity {
    private AsyncTask<String, String, List<String>> asyncTask;
    private String username;
    private FriendRequestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        setContentView(R.layout.activity_friend_request);
        FriendRequestAsyncTaskRunner runner = new FriendRequestAsyncTaskRunner();
        asyncTask = runner.execute(username);
        try {
            List<String> requests = asyncTask.get();
            this.adapter = new FriendRequestAdapter(this, requests, username);
            //show number of friend requests
            setListAdapter(adapter);
//            ListView listView  = (ListView) findViewById(R.id.listview);
//            listView.setAdapter(adapter);
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("InterruptedException", e.getMessage());
        }
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


}
