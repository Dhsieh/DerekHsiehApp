package AsyncTaskRunners;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by derekhsieh on 6/8/15.
 */

//handles login and signup activities
public class LoginSignUpAsyncTaskRunner extends AsyncTaskRunner<String, String, String> {
    private Context context;
    private HttpClient client;
    private HttpPost post;
    private Gson gson;


    public LoginSignUpAsyncTaskRunner(Context context) {
        this.context = context;
        gson = new Gson();
    }

    @Override
    protected String doInBackground(String... params) {
        List<String> responseList = null;
        int parameters = params.length;
        if (getClient(parameters)) {
            if (parameters == 2) {
                Log.i("Login", "Starting Login Process");
                List<NameValuePair> toPost = new ArrayList<>();
                toPost.add(new BasicNameValuePair("username", params[0]));
                toPost.add(new BasicNameValuePair("password", params[1]));
                try {
                    post.setEntity(new UrlEncodedFormEntity(toPost));
                    Log.i("post", post.toString());
                    HttpResponse response = client.execute(post);
                    responseList = readResponse(response);
                } catch (UnsupportedEncodingException e) {
                    Log.e("UnsupportedEncoding", e.getMessage());
                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocolException", e.getMessage());
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                }
            } else if (parameters == 5) {
                List<NameValuePair> toPost = new ArrayList<>();
                toPost.add(new BasicNameValuePair("username", params[0]));
                toPost.add(new BasicNameValuePair("password", params[1]));
                toPost.add(new BasicNameValuePair("email", params[2]));
                toPost.add(new BasicNameValuePair("first_name", params[3]));
                toPost.add(new BasicNameValuePair("last_name", params[4]));
                try {
                    post.setEntity(new UrlEncodedFormEntity(toPost));
                    HttpResponse response = client.execute(post);
                    responseList = readResponse(response);
                } catch (UnsupportedEncodingException e) {
                    Log.e("UnsupportedEncoding", e.getMessage());
                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocolException", e.getMessage());
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                }
            }
            return gson.toJson(responseList);
        } else {
            Log.e("No network available", "Could not access the internet, check the wifi connection");
            return null;
        }
    }

    @Override
    public boolean getClient(int params) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            client = new DefaultHttpClient();
            if (params == 2)
<<<<<<< HEAD
                post = new HttpPost("http://192.168.0.115:4567/Login");
            else
                post = new HttpPost("https://192.168.0.115:4567/AddUser");
=======
                post = new HttpPost("http://" + ipAddress + "/Login");
            else
                post = new HttpPost("https://107.178.215.255:4567/AddUser");
>>>>>>> master
            Log.i("received conn", "true");
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
            return true;
        } else {
            //do something
            Log.i("received conn", "false");
            return false;
        }
    }

    @Override
    public List<String> readResponse(HttpResponse response) {
        ArrayList<String> toReturn = new ArrayList<>();
        try {
            String responseString = EntityUtils.toString(response.getEntity());
            String[] splitResponse = responseString.split("\\t");
            for(int i = 0; i < splitResponse.length; i++){
                toReturn.add(splitResponse[i]);
            }
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return toReturn;
    }


}
