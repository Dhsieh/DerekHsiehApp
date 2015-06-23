package AsyncTaskRunners;

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
 */
public class FriendRequestAsyncTaskRunner extends AsyncTask<String, String, List<String>> {
    @Override
    //param 0 = username
    protected List<String> doInBackground(String... params) {
        int noParams = params.length;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://192.168.1.103:8080/Servlet/FriendRequestServlet");
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

    private List<String> readResponse(HttpResponse response) {
        try {
            byte[] serializedFriendRequests = EntityUtils.toByteArray(response.getEntity());
            return (List<String>) Serializer.toObject(serializedFriendRequests);
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return new ArrayList<>();
    }

}
