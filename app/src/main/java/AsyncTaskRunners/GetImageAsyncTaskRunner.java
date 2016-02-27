package AsyncTaskRunners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phoenix on 2/13/16.
 */
public class GetImageAsyncTaskRunner extends AsyncTaskRunner<String, String, String, String> {
    // TODO: Build in protection against no image given a user and friend
    private Context context;
    HttpClient client;
    HttpPost post;

    public GetImageAsyncTaskRunner(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String responseString = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://" + ipAddress + "/GetPhoto");
        List<NameValuePair> toPost = new ArrayList<>();
        toPost.add(new BasicNameValuePair("user", params[0]));
        toPost.add(new BasicNameValuePair("friend", params[1]));
        try {
            post.setEntity(new UrlEncodedFormEntity(toPost));
            Log.i("post", post.toString());
            HttpResponse response = client.execute(post);
            responseString = readResponse(response);
            return responseString;
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return responseString;

    }

    @Override
    protected boolean getClient(int params) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            client = new DefaultHttpClient();
            post = new HttpPost("http://" + ipAddress + "/GetPhoto");
            return true;
        } else {
            //do something
            return false;
        }
    }

    @Override
    protected String readResponse(HttpResponse response) {
        try {
            String responseString = EntityUtils.toString(response.getEntity());
            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
