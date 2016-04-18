package derekhsieh.derekhsiehapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class StartNewHunt extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView txt = (TextView) findViewById(R.id.login_Title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Aliquam.ttf");
        txt.setTypeface(font);
        setContentView(R.layout.activity_start_new_hunt);
    }

}
