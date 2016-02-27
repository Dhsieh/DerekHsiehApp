package AsyncTaskRunners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import Serializer.Serializer;

/**
 * Created by phoenix on 12/23/15.
 */
public class PostImageAsyncTaskRunner extends AsyncTaskRunner<String, String, Boolean, List<String>> {
    private Context context;
    HttpClient client;
    HttpPost post;

    public PostImageAsyncTaskRunner(Context context) {

    }

    @Override
    //param 0 = username
    protected Boolean doInBackground(String... params) {
        List<String> responseList = null;
        int noParams = params.length;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://" + ipAddress + "/SendPhoto");
        List<NameValuePair> toPost = new ArrayList<>();
        toPost.add(new BasicNameValuePair("user", params[0]));
        toPost.add(new BasicNameValuePair("friend", params[1]));
        toPost.add(new BasicNameValuePair("photo", params[2]));
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
        return false;

    }

    @Override
    protected boolean getClient(int params) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            client = new DefaultHttpClient();
            post = new HttpPost("https://" + ipAddress + "/Servlet/RetrievePhoto");
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

    protected Bitmap readResponseBitmap(HttpResponse response) {
        try {
            byte [] bitMapData = EntityUtils.toByteArray(response.getEntity());
            return BitmapFactory.decodeByteArray(bitMapData, 0, bitMapData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
