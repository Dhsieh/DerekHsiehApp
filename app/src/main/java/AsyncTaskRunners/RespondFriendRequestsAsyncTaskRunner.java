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

/**
 * Created by derekhsieh on 6/21/15.
 */
public class RespondFriendRequestsAsyncTaskRunner extends AsyncTask<String, String, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        int numParams = params.length;
        //accept

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://192.168.1.103:8080/Servlet/FriendRequestResponse");
        List<NameValuePair> toPost = new ArrayList<>();
        toPost.add(new BasicNameValuePair("username", params[0]));
        toPost.add(new BasicNameValuePair("status", params[1]));
        toPost.add(new BasicNameValuePair("firend", params[2]));
        boolean status = false;
        try {
            post.setEntity(new UrlEncodedFormEntity(toPost));
            HttpResponse response = client.execute(post);
            status = readResponse(response);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocol", e.getMessage());
        } catch (IOException e) {
            Log.e("IOExcxeption", e.getMessage());
        }
        return status;
    }

    private boolean readResponse(HttpResponse response) throws IOException {
        return Boolean.parseBoolean(EntityUtils.toString(response.getEntity()));
    }
}
