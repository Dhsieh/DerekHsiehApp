package AsyncTaskRunners;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Serializer.Serializer;

/**
 * Created by derekhsieh on 6/18/15.
 * Handles getting a user's friends request if there are any.
 */
public class FriendRequestAsyncTaskRunner extends AsyncTaskRunner<String, String, List<String>> {
    private Context context;
    HttpClient client;
    HttpPost post;

    public FriendRequestAsyncTaskRunner(Context context) {

    }

    @Override
    //param 0 = username
    protected List<String> doInBackground(String... params) {
        int noParams = params.length;
        HttpClient client = new DefaultHttpClient();
<<<<<<< HEAD
        HttpPost post = new HttpPost("https://192.168.0.115::4567/Servlet/FriendRequest");
=======
        HttpPost post = new HttpPost("https://" + ipAddress + "/Servlet/FriendRequest");
>>>>>>> master
        List<NameValuePair> toPost = new ArrayList<>();
        toPost.add(new BasicNameValuePair("username", params[0]));

        try {
            post.setEntity(new UrlEncodedFormEntity(toPost));
            HttpResponse response = client.execute(post);
            return readResponse(response);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return new ArrayList<String>();

    }

    @Override
    protected boolean getClient(int params) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            client = new DefaultHttpClient();
<<<<<<< HEAD
            post = new HttpPost("https://192.168.0.115::4567/Servlet/FreindRequest");
=======
            post = new HttpPost("https://" + ipAddress + "/Servlet/Friend");
>>>>>>> master
            return true;
        } else {
            //do something
            return false;
        }
    }

    @Override
    protected List<String> readResponse(HttpResponse response) {
        try {
            byte[] serializedFriendRequests = EntityUtils.toByteArray(response.getEntity());
            return (List<String>) Serializer.toObject(serializedFriendRequests);
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return new ArrayList<>();
    }

}
