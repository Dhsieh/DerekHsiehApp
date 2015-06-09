package derekhsieh.derekhsiehapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void login(View view) throws IOException {
        String user = ((EditText) findViewById(R.id.Username)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();

        if (user.equals("") || password.equals("")) {
            Toast noUserOrPass = Toast.makeText(this, "User or password is empty", Toast.LENGTH_LONG);
            noUserOrPass.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
            noUserOrPass.show();
        } else {
            Toast hasUserAndPass = Toast.makeText(this, "User and password is not empty!", Toast.LENGTH_LONG);
            hasUserAndPass.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 175, 150);
            hasUserAndPass.show();
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://192.168.1.103:8080/Servlet/LoginServlet");
            List<NameValuePair> toPost = new ArrayList<>();
            toPost.add(new BasicNameValuePair("username", user));
            toPost.add(new BasicNameValuePair("password", password));
            post.setEntity(new UrlEncodedFormEntity(toPost));
            HttpResponse response = client.execute(post);
            String answer = readResponse(response);
            if (answer.contains("true")) {
                Toast.makeText(getApplicationContext(), "correct!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Wrong user or password", Toast.LENGTH_SHORT).show();


        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
