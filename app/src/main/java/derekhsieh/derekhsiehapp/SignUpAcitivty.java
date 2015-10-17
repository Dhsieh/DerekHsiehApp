package derekhsieh.derekhsiehapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import AsyncTaskRunners.LoginSignUpAsyncTaskRunner;


public class SignUpAcitivty extends ActionBarActivity {
    private AsyncTask<String, String, String> asyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            ((EditText) findViewById(R.id.Username)).setText(savedInstanceState.getString("username"), TextView.BufferType.EDITABLE.EDITABLE);
            ((EditText) findViewById(R.id.Password)).setText(savedInstanceState.getString("password"), TextView.BufferType.EDITABLE.EDITABLE);
            ((EditText) findViewById(R.id.Email)).setText(savedInstanceState.getString("email"), TextView.BufferType.EDITABLE.EDITABLE);
            ((EditText) findViewById(R.id.FirstName)).setText(savedInstanceState.getString("first_name"), TextView.BufferType.EDITABLE.EDITABLE);
            ((EditText) findViewById(R.id.LastName)).setText(savedInstanceState.getString("last_name"), TextView.BufferType.EDITABLE.EDITABLE);

        } else
            setContentView(R.layout.activity_sign_up_acitivty);
    }


    public void signUp(View view) throws ExecutionException, InterruptedException {
        String user = ((EditText) findViewById(R.id.Username)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();
        String email = ((EditText) findViewById(R.id.Email)).getText().toString();
        String first_name = ((EditText) findViewById(R.id.FirstName)).getText().toString();
        String last_name = ((EditText) findViewById(R.id.LastName)).getText().toString();


        if (user.equals("") || password.equals("")) {
            Toast noUserOrPass = Toast.makeText(this, "User or password is empty", Toast.LENGTH_LONG);
            noUserOrPass.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
            noUserOrPass.show();
        } else {
            LoginSignUpAsyncTaskRunner runner = new LoginSignUpAsyncTaskRunner(getApplicationContext());
            asyncTask = runner.execute(user, password, email, first_name, last_name, "signup");
            String answer = asyncTask.get();
            if (answer.contains("true"))
                Toast.makeText(getApplicationContext(), "User has been added", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "User has not been added", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_acitivty, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String user = ((EditText) findViewById(R.id.Username)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();
        String email = ((EditText) findViewById(R.id.Email)).getText().toString();
        String first_name = ((EditText) findViewById(R.id.FirstName)).getText().toString();
        String last_name = ((EditText) findViewById(R.id.LastName)).getText().toString();

        savedInstanceState.putString("user", user);
        savedInstanceState.putString("password", password);
        savedInstanceState.putString("email", email);
        savedInstanceState.putString("first_name", first_name);
        savedInstanceState.putString("last_name", last_name);
        System.out.println();
        super.onSaveInstanceState(savedInstanceState);
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
