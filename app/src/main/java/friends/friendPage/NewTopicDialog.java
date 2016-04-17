package friends.friendPage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import derekhsieh.derekhsiehapp.R;

/**
 * Created by phoenix on 3/30/16.
 * Custom dialog that has a line edit to capture the new topic
 */
public class NewTopicDialog extends Dialog{

    OnNewTopicDialogResult mDialogResult;

    public NewTopicDialog(Context context) {
        super(context);
    }

    // Allows the dialog to communicate data to the activity. Need to implement this method in the activity.
    public interface OnNewTopicDialogResult {
        void finish(String result);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_new_topic);
        setTitle("Rate Image");

        final EditText newTopicEditText = (EditText) findViewById(R.id.newTopicEditText);

        Button newTopicDialogCancelButton = (Button) findViewById(R.id.newTopicDialogCancelButton);

        // if cancel button is clicked, close the dialog
        newTopicDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTopicDialog.this.dismiss();
            }
        });

        Button newTopicDialogOKButton = (Button) findViewById(R.id.newTopicDialogOKButton);
        // if ok button is clicked, pass the topic
        newTopicDialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogResult.finish(String.valueOf(newTopicEditText.getText()));
                NewTopicDialog.this.dismiss();
            }
        });
    }

    // method for calling the interface
    public void setDialogResult(OnNewTopicDialogResult dialogResult){
        mDialogResult = dialogResult;
    }
}
