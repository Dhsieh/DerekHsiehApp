package AsyncTaskRunners;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import Utils.Serializer;

/**
 * Created by phoenix on 3/30/16.
 * Handles getting the topic that a friend sent for a new Scavenger Hunt
 */
public class GetRatingAsyncTaskRunner extends AsyncTaskRunner<String, String, List<String>, List<String>> {
    private Context context;
    HttpClient client;
    HttpPost post;

    public GetRatingAsyncTaskRunner(Context context) {

    }

    @Override
    //param 0 = username
    protected List<String> doInBackground(String... params) {
        int noParams = params.length;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://" + ipAddress + "/GetRating");
        List<NameValuePair> toPost = new ArrayList<>();
        toPost.add(new BasicNameValuePair("username", params[0]));
        toPost.add(new BasicNameValuePair("friend", params[1]));

        try {
            // TODO: Stub. Need to implement actual server reply
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
        // TODO: Stub. Need to implement actual server reply
        return new ArrayList<String>();

    }

    @Override
    protected boolean getClient(int params) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            client = new DefaultHttpClient();
            post = new HttpPost("http://" + ipAddress + "/GetRating");
            return true;
        } else {
            //do something
            return false;
        }
    }

    @Override
    protected List<String> readResponse(HttpResponse response) {
        try {
            // TODO: Stub. Need to implement with actual server response if necessary
            byte[] serializedFriendRequests = EntityUtils.toByteArray(response.getEntity());
            return (List<String>) Serializer.toObject(serializedFriendRequests);
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return new ArrayList<>();
    }

}
