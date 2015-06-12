package derekhsieh.derekhsiehapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity {
    private Button login;
    private AsyncTask<String, String, String> asyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void login(View view) throws ExecutionException, InterruptedException {
        String user = ((EditText) findViewById(R.id.Username)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();

        if (user.equals("") || password.equals("")) {
            Toast noUserOrPass = Toast.makeText(this, "User or password is empty", Toast.LENGTH_LONG);
            noUserOrPass.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
            noUserOrPass.show();
        } else {
            AsyncTaskRunner runner = new AsyncTaskRunner();
            asyncTask = runner.execute(user, password);
            String answer = asyncTask.get();
            if (answer.contains("true")) {
                Toast.makeText(getApplicationContext(), "correct!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Wrong user or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void signUp(View view) throws ExecutionException, InterruptedException {
        Intent intent = new Intent(this, SignUpAcitivty.class);
        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
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
