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

/**
 Created by derekhsieh on 6/21/15.
 TaskRunner that sends to the server whether some one accepted a friend request or not
 */
public class RespondFriendRequestsAsyncTaskRunner extends AsyncTaskRunner<String, String, Boolean> {
    private Context context;
    private HttpClient client;
    private HttpPost post;

    @Override
    protected Boolean doInBackground(String... params) {
        //accept
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://192.168.0.115:4567/Servlet/FriendRequestResponse");
        List<NameValuePair> toPost = new ArrayList<>();
        toPost.add(new BasicNameValuePair("username", params[0]));
        toPost.add(new BasicNameValuePair("status", params[1]));
        toPost.add(new BasicNameValuePair("firend", params[2]));
        boolean status = false;
        try {
            if(getClient(params.length))
            post.setEntity(new UrlEncodedFormEntity(toPost));
            HttpResponse response = client.execute(post);

        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocol", e.getMessage());
        } catch (IOException e) {
            Log.e("IOExcxeption", e.getMessage());
        }
        return status;
    }


    protected boolean getClient(int params) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            client = new DefaultHttpClient();
            post = new HttpPost("https://192.168.0.115:4567/Servlet/FriendRequestResponse");
            return true;
        } else {
            //do something
            return false;
        }
    }

    @Override
    protected List<String> readResponse(HttpResponse response) {
        return null;
    }
}
