package AsyncTaskRunners;

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
public class LoginSignUpAsyncTaskRunner extends AsyncTask<String, String, String> {
    private Gson gson = new Gson();
    @Override
    protected String doInBackground(String... params) {
        List<String> responseList = null;
        int parameters = params.length;
        if (parameters == 2) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://192.168.1.103:8080/Servlet/LoginServlet");
            List<NameValuePair> toPost = new ArrayList<>();
            toPost.add(new BasicNameValuePair("username", params[0]));
            toPost.add(new BasicNameValuePair("password", params[1]));
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
        } else if (parameters == 5) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://192.168.1.103:8080/Servlet/LoginServlet");
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
    }

    private List<String> readResponse(HttpResponse response) {
        ArrayList<String> toReturn = new ArrayList<>();
        InputStream is = null;
        String return_text = "";
        try {
            is = response.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                toReturn.add(line);
            }
        } catch (Exception e) {

        }
        return toReturn;

    }

}
