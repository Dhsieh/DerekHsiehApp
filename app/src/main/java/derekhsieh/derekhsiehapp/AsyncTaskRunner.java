package derekhsieh.derekhsiehapp;

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
public class AsyncTaskRunner extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        String toReturn = "";
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
                toReturn = readResponse(response);
            } catch (UnsupportedEncodingException e) {
                Log.e("UnsupportedEncoding", e.getMessage());
            } catch (ClientProtocolException e) {
               Log.e("ClientProtocolException", e.getMessage());
            } catch (IOException e) {
               Log.e("IOException", e.getMessage());
            }
        }else if(parameters == 3){

        } HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://192.168.1.103:8080/Servlet/LoginServlet");
        List<NameValuePair> toPost = new ArrayList<>();
        toPost.add(new BasicNameValuePair("username", params[0]));
        toPost.add(new BasicNameValuePair("password", params[1]));
        try {
            post.setEntity(new UrlEncodedFormEntity(toPost));
            HttpResponse response = client.execute(post);
            toReturn = readResponse(response);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }

        return toReturn;
    }

    private String readResponse(HttpResponse response) {
        InputStream is = null;
        String return_text = "";
        try {
            is = response.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
        } catch (Exception e) {

        }
        return return_text;

    }

}
