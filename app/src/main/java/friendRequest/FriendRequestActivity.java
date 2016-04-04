package friendRequest;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");


        OkHttpClient okClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.103:4567")
                .addConverterFactory(GsonConverterFactory.create()).client(okClient).build();

        GetMethod toGet = retrofit.create(GetMethod.class);
        Call<List<String>> call = toGet.getResponse("GetFriendRequests", username);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
                if (response.isSuccessful()) {
                    setContentView(R.layout.activity_friend_request);
                    friendRequests = response.body();
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


//        FriendRequestAsyncTaskRunner runner = new FriendRequestAsyncTaskRunner(getApplicationContext());
//        asyncTask = runner.execute(username);
//        try {
//            List<String> requests = asyncTask.get();
//            this.adapter = new FriendRequestAdapter(this, requests, username);
//            //show number of friend requests
//            setListAdapter(adapter);
////            ListView listView  = (ListView) findViewById(R.id.listview);
////            listView.setAdapter(adapter);
//        } catch (InterruptedException e) {
//            Log.e("InterruptedException", e.getMessage());
//        } catch (ExecutionException e) {
//            Log.e("InterruptedException", e.getMessage());
//        }
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
