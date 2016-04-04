package login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import RetroFitInterfaces.RetroFitInterface;
import derekhsieh.derekhsiehapp.MainPageActivity;
import derekhsieh.derekhsiehapp.R;
import derekhsieh.derekhsiehapp.SignUpAcitivty;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends Activity {
    private Button login;
    private AsyncTask<String, String, String> asyncTask;
    private Gson gson;
    private LoginResponse loginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gson = new Gson();
    }


    public void login(View view) throws ExecutionException, InterruptedException {
        final String username = ((EditText) findViewById(R.id.Username)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast noUserOrPass = Toast.makeText(this, "User or password is empty", Toast.LENGTH_LONG);
            noUserOrPass.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
            noUserOrPass.show();
        } else {

            Retrofit retrofit = RetroFitInterface.createRetroFit();
            PostMethod toPost = retrofit.create(PostMethod.class);
            Call<LoginResponse> response = toPost.postResponse("Login", new Login(username, password));
            response.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        loginResponse = response.body();
                        if (loginResponse.isSuccess()) {
                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                            intent.putExtra("friendRequests", loginResponse.getFriendRequests());
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong user or password", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Log.e("Login Failed", "Could not login for user " + username);
                        loginResponse = new LoginResponse();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    loginResponse = new LoginResponse();
                }
            });

        }
    }

    public void signUp(View view) throws ExecutionException, InterruptedException {
        Intent intent = new Intent(this, SignUpAcitivty.class);
        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String user = ((EditText) findViewById(R.id.Username)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();

        savedInstanceState.putString("user", user);
        savedInstanceState.putString("password", password);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
